/*
 * File: $URL: https://vklgraph.googlecode.com/svn/trunk/src/main/java/fr/vekia/VkGraph/client/charts/Chart.java $
 * $Id: Chart.java 37 2012-09-07 07:35:08Z steeve.vandecappelle@gmail.com $
 * Licence MIT
 * 
 * Last change:
 * $Date: 2012-09-07 09:35:08 +0200 (ven., 07 sept. 2012) $
 * $Author: steeve.vandecappelle@gmail.com $
 */
package fr.vekia.VkGraph.client.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import fr.vekia.VkGraph.client.charts.events.AttachedChartEvent;
import fr.vekia.VkGraph.client.charts.events.AttachedChartHandler;
import fr.vekia.VkGraph.client.charts.events.HasAttachedChartEventHandlers;
import fr.vekia.VkGraph.client.charts.events.JqPlotEvent;
import fr.vekia.VkGraph.client.charts.exception.ArraysSizesRefreshingInvalidException;
import fr.vekia.VkGraph.client.charts.menus.MenuCommands;
import fr.vekia.VkGraph.client.datas.DualValue;
import fr.vekia.VkGraph.client.datas.utils.ArrayJSONBuilder;
import fr.vekia.VkGraph.client.options.ChartOption;
import fr.vekia.VkGraph.client.options.SubOption;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 4 mai 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
abstract class Chart<T> extends SimplePanel implements HasAttachedChartEventHandlers {

    private static final String DEFAULT_HEIGHT = "600px";
    private static final String DEFAULT_WIDTH = "800px";
    private static final int DESIGNER_PANEL_POSITION = -10000;


    // Injection flag
    private boolean injected = false;
    // Graph ID
    private final String id = DOM.createUniqueId();

    // ## UIs ##

    //
    private SimplePanel chartLayout;
    // Chart panel DIV container
    private SimplePanel chartContainer;
    // container resized when resize event are binded
    private SimplePanel resizableContainer;

    // Events binded with the chart.
    private List<JqPlotEvent> bindedEvents;
    // Instance of JavaScript Chart Object.
    private JavaScriptObject chartJavascriptObject;
    // the active theme if any
    private String theme;

    // chart renderer. The default renderer is LineChart
    private RenderersEnum renderer = RenderersEnum.Line;

    // Plug-ins enable (Trend-line / DragData)
    private boolean isPluginEnable;
    // true if the chart should have resize behavior.
    private boolean isResizableChart;

    // chart Options
    private ChartOptioner chartOptionner;
    // menu right click
    private ChartRightMenuController rightMenuController;
    // Exporter to PNG image
    private Exporter exporter;
    // event jQplot JavaScript binder
    private EventBinder eventBinder;
    // the data controller
    private DataController<T> dataController;
    // the resize JQuery controller
    private Resizer resizer;
    // zoom proxy
    private ZoomProxy proxyZoom;

    // Internationalisaton Right click menu.
    private I18nFields i18nFields;

    public Chart() {
        this(null);
    }

    /**
     * Default constructor
     * 
     */
    public Chart(I18nFields i18nFields) {
    	this.chartLayout = new SimplePanel();
    	this.chartContainer = new SimplePanel();
    	
    	this.chartLayout.setSize("100%","100%");
    	this.chartContainer.setSize("100%","100%");
    	
    	this.resizableContainer = new SimplePanel(this.chartContainer);
    	super.setSize("100%", "100%");
    	

        // charts controllers

    	// option controller
    	this.chartOptionner = new ChartOptioner();
    	// data controller
    	this.dataController = new DataController<T>(this);
    	// export to image controller
    	this.exporter = new Exporter(this);
    	// event controller / binder
    	this.eventBinder = new EventBinder(this);
    	// specific resize event controller
    	this.resizer = new Resizer(this);
    	
        // right click controller
        if(i18nFields !=null ){
            this.rightMenuController = new ChartRightMenuController(i18nFields);
        }else{
            this.rightMenuController = new ChartRightMenuController();
        }

    	// set HTML Id
    	this.chartContainer.getElement().setId(id);
    	this.resizableContainer.getElement().setId(id + "resizable");

    	// Stylish
        this.resizableContainer.addStyleName("vkl-ChartContainer");
    	
        // size of graph should have a pixel default defined else 0% will be applied and graph is invisible.
    	this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    	this.resizableContainer.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    	// attach UI
    	this.chartLayout.setWidget(this.resizableContainer);
    	this.setWidget(this.chartLayout);
    	
    	this.addStyleName("Vkl-Graph");
    }

  private native void activateTheme(JavaScriptObject chart, String themeName)/*-{
        // theme activated flag
        var flag = false;
        try {
            //  insert the theme into the available list of JqPlot themes
            if (chart.themeEngine.get(themeName) != null) {
                chart.themeEngine.remove(themeName);
            }
            var themes = new $wnd.VklThemes();
            chart.themeEngine.newTheme(themeName, themes.get(themeName, chart));
        } catch (err) {
            flag = true;
        }
        // activate the theme added
        chart.activateTheme(themeName);
    }-*/;

    private native void activateTheme(JavaScriptObject chart, String themeName, JavaScriptObject themeJavascript)/*-{
        // theme activated flag
        var flag = false;
        try {
            // try to activate a theme
            if (chart.themeEngine.get(themeName) != null) {
        	   chart.themeEngine.remove(themeName);
            }
            //  insert the theme into the available list of JqPlot themes
            chart.themeEngine.newTheme(themeName, themeJavascript);
        } catch (err) {
            flag = true;
        }
        // activate the theme added
        chart.activateTheme(themeName);
    }-*/;

    public void activateTheme(String themeName, JavaScriptObject themeJavascript) {
    	try {
    	    // if chart is already attached to the browser attach the theme to the JavaScript chart. It will be attached onAttach Event else
    	    if (this.chartJavascriptObject != null) {
    	       activateTheme(this.chartJavascriptObject, themeName, themeJavascript);
    	    } else {
        		this.theme = themeName;
    	    }
    	} catch (Exception e) {
    	     GWT.log("Activation theme may be with error: " + e.getMessage());
    	     JsConsole.warn("W101TA", themeName, "Activation theme may be with error: " + e.getMessage());
    	}
    }

    /**
     * Activate a theme with is theme name.
     * 
     * @param themeName
     *            the theme name to activate.
     */
    public void activateTheme(String themeName) {
    	try {
    	    // if chart is already attached to the browser attach the theme to the JavaScript chart. It will be attached onAttach Event else
    	    if (this.chartJavascriptObject != null) {
    		  activateTheme(this.chartJavascriptObject, themeName);
    	    } else {
    		  this.theme = themeName;
    	    }
    	} catch (Exception e) {
            GWT.log("Activation theme may be with error: " + e.getMessage());
            JsConsole.warn("W101TA", themeName, "Activation theme may be with error: " + e.getMessage());
    	}
    }

    /**
     * Add a custom command to the right click ChartMenu.
     * 
     * @param command
     */
    public void addToRightClickMenu(MenuCommands command) {
    	this.rightMenuController.activate(this, this.chartLayout);
    	this.rightMenuController.addToMenu(command);
    }

    /**
     * @param event
     */
    public void bind(JqPlotEvent event) {
    	if (this.bindedEvents == null) {
    	    this.bindedEvents = new ArrayList<JqPlotEvent>();
    	}
    	this.bindedEvents.add(event);
    }

    /**
     * Bind the chart resize event.
     */
    private void bindResize() {
    	if (this.isResizableChart) {
    	    this.chartContainer.setSize("100%", "100%");
    	    this.resizer.bind();
    	}
    }

    /**
     * Binds all events.
     */
    private void binds() {
    	if (this.bindedEvents != null) {
    	    this.eventBinder.bind(this.bindedEvents);
    	}
    }

    /**
     * @param elementId
     * @param dataString
     * @param options
     * @param isPluginEnable
     * @return
     */
    private native JavaScriptObject callJqPlot(String elementId, JavaScriptObject dataString, JavaScriptObject options, boolean isPluginEnable, String themeName)/*-{
        // plugins JqPlot
        $wnd.jQuery.jqplot.config.enablePlugins = isPluginEnable;

        // Chart generated by JqPlot library
        var chartJavascript = $wnd.jQuery.jqplot(elementId, dataString, options);

        // activate theme if any
        if (themeName != null) {

            // try to activate a theme
            if (chartJavascript.themeEngine.get(themeName) != null) {
               chartJavascript.themeEngine.remove(themeName);
            }
            var themes = new $wnd.VklThemes();
        	chartJavascript.themeEngine.newTheme(themeName, themes.get(themeName, chartJavascript));
            chartJavascript.activateTheme(themeName);
        }
        // plugins JqPlot reset to false prevent any other chart added to DOM.
        
        $wnd.jQuery.jqplot.config.enablePlugins = false;
        return chartJavascript;
    }-*/;

    public void setZoomProxy(final Chart<?> chartProxy) {
    	this.proxyZoom = new ZoomProxy();
    	this.proxyZoom.setProxy(this, chartProxy);
    }

    /**
     * @param option
     * @param value
     * @param chart
     */
    private native void changeProperty(JavaScriptObject optionArray, JavaScriptObject value, JavaScriptObject chart)/*-{
        var functionVar = chart;
        for ( var optionNb = 0; optionNb < optionArray.length - 1; optionNb++) {
            functionVar = functionVar[optionArray[optionNb]];
        }
        functionVar[optionArray[optionArray.length - 1]] = value;
    }-*/;

    /**
     * @param option
     * @param value
     * @param chart
     */
    private native void changeProperty(JavaScriptObject optionArray, Number value, JavaScriptObject chart)/*-{
        var functionVar = chart;
        for ( var optionNb = 0; optionNb < optionArray.length - 1; optionNb++) {
            functionVar = functionVar[optionArray[optionNb]];
        }
        functionVar[optionArray[optionArray.length - 1]] = value;
    }-*/;

    /**
     * @param option
     * @param value
     * @param chart
     */
    private native void changeProperty(JavaScriptObject optionArray, String value, JavaScriptObject chart)/*-{
        var functionVar = chart;
        for ( var optionNb = 0; optionNb < optionArray.length - 1; optionNb++) {
            functionVar = functionVar[optionArray[optionNb]];
        }
        functionVar[optionArray[optionArray.length - 1]] = value;
    }-*/;

    /**
     * @param arrayData
     * @param string
     */
    protected void changeProperty(JSONArray arrayData, String... option) {
    	ArrayJSONBuilder<String> optionsArray = new ArrayJSONBuilder<String>();
    	optionsArray.setData(Arrays.asList(option));
    	changeProperty(optionsArray.getJso().getJavaScriptObject(), arrayData.getJavaScriptObject(), this.chartJavascriptObject);
    }

    /**
     * @param arrayData
     * @param string
     */
    protected void changeProperty(JSONObject arrayData, String... option) {
    	ArrayJSONBuilder<String> optionsArray = new ArrayJSONBuilder<String>();
    	optionsArray.setData(Arrays.asList(option));
    	changeProperty(optionsArray.getJso().getJavaScriptObject(), arrayData.getJavaScriptObject(), this.chartJavascriptObject);
    }

    /**
     * @param value
     * @param name
     * @param name2
     * @param name3
     */
    protected void changeProperty(Number value, String... option) {
    	ArrayJSONBuilder<String> optionsArray = new ArrayJSONBuilder<String>();
    	optionsArray.setData(Arrays.asList(option));
    	changeProperty(optionsArray.getJso().getJavaScriptObject(), value, this.chartJavascriptObject);
    }

    /**
     * @param datasSeries
     */
    protected void changeProperty(String datasSeries, String... option) {
    	ArrayJSONBuilder<String> optionsArray = new ArrayJSONBuilder<String>();
    	optionsArray.setData(Arrays.asList(option));
    	changeProperty(optionsArray.getJso().getJavaScriptObject(), datasSeries, this.chartJavascriptObject);
    }

    /**
     * @return the chartPanelContainer
     */
    protected SimplePanel getChartPanelContainer() {
	   return this.chartContainer;
    }

    /**
     * @return the id
     */
    public String getId() {
	   return this.id;
    }

    /**
     * Call the JavaScript in a deferred command. To insure the browser has attach all depends containers.
     */
    private void inject() {
    	// Inject the code when the navigator is ready to inject with a visible chart.
    	Scheduler.get().scheduleDeferred(new ScheduledCommand() {

    	    @Override
    	    public void execute() {
    		  injectionChart();
    	    }
    	});
    }

    /**
     * Call the JavaScript in a deferred command. To insure the browser has attach all depends containers. and attach to the browser the result
     * builded by jQplot.
     */
    private void injectionChart() {
    	// the designer panel is used to build the chart into a different part of browser to prevent any GWT Layout which used OffSet Width/Height
    	final AbsolutePanel designerPanel = new AbsolutePanel();
    	designerPanel.getElement().getStyle().setZIndex(-1);
    	designerPanel.add(this.chartContainer);
        
        

    	// The designer panel should not be visible for the user, and be removed after the chart generation.
    	RootPanel.get().add(designerPanel);
    	RootPanel.get().setWidgetPosition(designerPanel, DESIGNER_PANEL_POSITION, DESIGNER_PANEL_POSITION);

    	boolean visible = isVisible();
    	boolean isNotInjected = !this.injected;
    	boolean attached = isAttached();
    	boolean isWidthVisible = Chart.this.getOffsetWidth() > 0;

        // The chart is repositioned to his previous position.
        Chart.this.resizableContainer.setWidget(Chart.this.chartContainer);
        // remove the designer temporary panel.
        designerPanel.removeFromParent();
        // bind resize event if any.
        Chart.this.bindResize();

        // if the container is visible and not already injected into DOM the chart could be created
        if (visible && isNotInjected && attached && isWidthVisible) {
            //designerPanel.setSize(Chart.this.getOffsetWidth()+"px",Chart.this.getOffsetHeight()+"px");
            JsConsole.info(Chart.this.getOffsetWidth()+"px");
            JsConsole.info(Chart.this.getOffsetHeight()+"px");
            Chart.this.chartJavascriptObject = Chart.this.callJqPlot(getId(), dataController.getInjectionData(), dataController.getInjectionOptions(), isPluginEnable, theme);
            JsConsole.info("chart '" + getId() + "' successfully created and added to DOM.");
            Chart.this.injected = true;

            // fire an event to prevent children than the chart was injected.
            Chart.this.fireEvent(new AttachedChartEvent());    
        }

    }

    /**
     * 
     * 
     * @return <code>true</code> if the chart is JavaScript called.
     */
    protected boolean isInjected() {
    	return this.injected;
    }

    @Override
    protected void onAttach() {
    	super.onAttach();
    	// inject the chart if not yet injected.
    	this.inject();
    	// binds chart events.
    	this.binds();
    }

    /**
     * 
     */
    public void replot() {
    	if (this.chartJavascriptObject != null) {
    	    replot(this.chartJavascriptObject);
    	} else {
    	    inject();
    	}
    }

    public void replotYAxe(int minVal, int maxVal) {
	   this.replotYAxe(minVal, maxVal, 1, this.chartJavascriptObject);
    }

    public void replotXAxe(int minVal, int maxVal) {
	   this.replotXAxe(minVal, maxVal, 1, this.chartJavascriptObject);
    }

    public void replotYAxe(int minVal, int maxVal, int ticksInterval) {
	   this.replotYAxe(minVal, maxVal, ticksInterval, this.chartJavascriptObject);
    }

    public void replotXAxe(int minVal, int maxVal, int ticksInterval) {
	   this.replotXAxe(minVal, maxVal, ticksInterval, this.chartJavascriptObject);
    }

    public void replotWithAxe() {
	   this.replotWithAxe(this.chartJavascriptObject);
    }

    private native void replotWithAxe(JavaScriptObject chart)/*-{
    	 chart.replot( { resetAxes: true } );
    }-*/;

    private native void replotYAxe(int minVal, int maxVal, int ticksInterval, JavaScriptObject chart)/*-{
    	// replot axe y
    	chart.axes.yaxis.max = maxVal;
    	chart.axes.yaxis.min = minVal;
    	chart.axes.yaxis.tickInterval = ticksInterval;
    	chart.replot({resetAxes:['yaxis'], axes:{yaxis:{max:maxVal,min:minVal,tickInterval: ticksInterval}}});
    }-*/;

    private native void replotXAxe(int minVal, int maxVal, int ticksInterval, JavaScriptObject chart)/*-{
    	// replot axe x
    	chart.axes.xaxis.max = maxVal;
    	chart.axes.xaxis.min = minVal;
    	chart.axes.xaxis.tickInterval = ticksInterval;
    	chart.replot({resetAxes:['xaxis'], axes:{xaxis:{max:maxVal,min:minVal,tickInterval: ticksInterval}}});
    }-*/;

    /**
     * 
     */
    private native void replot(JavaScriptObject chart)/*-{
    	chart.replot();
    }-*/;

    /**
     * Set the chart data
     * 
     * @param data
     *            the data of chart needs to be displayed.
     */
    protected void setData(List<T> data) {
	   this.dataController.setData(data);
    }

    /**
     * Set the PNG image export enable or disable.
     * 
     * @param isEnable
     *            <code>true</code> to enable export.
     */
    public void setExportEnable(boolean isEnable) {
    	this.rightMenuController.activate(this, this.chartLayout);
    	this.rightMenuController.setExportEnable(this.exporter, isEnable);
    }

    @Override
    public void setHeight(String height) {
    	this.chartContainer.setHeight(height);
    	this.resizableContainer.setHeight(height);
    }

    /**
     * @param data
     *            the data to set
     * @throws ArraysSizesRefreshingInvalidException
     */
    public void setListData(List<List<T>> data) throws ArraysSizesRefreshingInvalidException {
	   this.dataController.setListData(data);
    }

    /**
     * @param isListView
     *            the isListView to set
     */
    public void setListView(boolean isListView) {
	   this.dataController.setListView(isListView);
    }

    /**
     * @param optionsMapped
     *            the optionsMapped to set
     */
    protected void setOptionsMapped(Map<ChartOption, Map<SubOption, String>> optionsMapped) {
	   this.chartOptionner.setOptionsMapped(optionsMapped);
    }

    public void setPluginsEnable(boolean isPluginEnable) {
	   this.isPluginEnable = isPluginEnable;
    }

    /**
     * Set the PNG refreshing deferred (replot) enable or disable.
     * 
     * @param isEnable
     *            <code>true</code> to enable refreshing deferred.
     */
    public void setRefreshManuallyEnable(boolean isEnable) {
    	rightMenuController.activate(this, chartLayout);
    	rightMenuController.setRefreshManualEnable(isEnable);
    }

    /**
     * @param renderer
     *            the renderer to set
     */
    public void setRenderer(RenderersEnum renderer) {
	   this.renderer = renderer;
    }

    /**
     * Set the chart has resize events and can be resize on the container.
     * 
     * @param isEnable
     *            <code>true</code> to add a resize behavior.
     */
    public void setResizable(boolean isEnable) {
        if(isEnable){
            this.resizableContainer.addStyleName("vkl-ResizableChart");
        }else{
            this.resizableContainer.removeStyleName("vkl-ResizableChart");
        }
	   this.isResizableChart = isEnable;
    }

    /**
     * @param i
     * @param arrayData
     * @param string
     * @param string2
     */
    private native void setSeries(int serieId, JavaScriptObject arrayData, JavaScriptObject chart)/*-{
    	chart.series[serieId].data = arrayData;
    	chart.resetAxesScale();
    }-*/;

    /**
     * @param i
     * @param arrayData
     * @param string
     * @param string2
     */
    public void setSeriesDataAfterInject(int serieId, List<DualValue> datas) {
    	ArrayJSONBuilder<DualValue> datasArray = new ArrayJSONBuilder<DualValue>();
    	datasArray.setData(datas);
    	setSeriesDataAfterInject(serieId, datasArray.getJso().getJavaScriptObject(), this.chartJavascriptObject);
    }

    /**
     * @param i
     * @param arrayData
     * @param string
     * @param string2
     */
    private native void setSeriesDataAfterInject(int serieId, JavaScriptObject arrayData, JavaScriptObject chart)/*-{
    	chart.series[serieId].data = arrayData;
    	chart.replot();
    }-*/;

    /**
     * @param i
     * @param arrayData
     */
    public void setSeries(int serieId, JSONArray arrayData) {
	   setSeries(serieId, arrayData.getJavaScriptObject(), this.chartJavascriptObject);
    }

    /**
     * @param seriesData
     *            the seriesData to set
     */
    public void setSeriesData(JSONArray seriesData) {
	   this.dataController.setSeriesData(seriesData);
    }

    @Override
    public final void setSize(String width, String height) {
    	this.chartContainer.setSize(width, height);
    	this.resizableContainer.setSize(width, height);
    }

    /**
     * @param subSubOptionsMapped
     *            the subSubOptionsMapped to set
     */
    protected void setSubSubOptionsMapped(Map<ChartOption, Map<SubOption, Map<SubOption, String>>> subSubOptionsMapped) {
	   this.chartOptionner.setSubSubOptionsMapped(subSubOptionsMapped);
    }

    /**
     * @param subSubSubOptionsMapped
     *            the subSubSubOptionsMapped to set
     */
    protected void setSubSubSubOptionsMapped(Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, String>>>> subSubSubOptionsMapped) {
	   this.chartOptionner.setSubSubSubOptionsMapped(subSubSubOptionsMapped);
    }

    /**
     * @return
     */
    public RenderersEnum getRenderer() {
	   return this.renderer;
    }

    /**
     * Set the chart themes change enable.
     * 
     * @param isEnable
     *            <code>true</code> to enable the theme changing.
     */
    public void setThemingEnable(boolean isEnable) {
    	this.rightMenuController.activate(this, this.chartLayout);
    	this.rightMenuController.setTemingEnable(isEnable);
    }

    public void setTheme(Theming theme){
        this.theme = theme.getTheme();
    }

    /**
     * @return the chartJavascriptObject
     */
    protected JavaScriptObject getChartJavascriptObject() {
	   return this.chartJavascriptObject;
    }

    @Override
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	this.inject();
    }

    @Override
    public void setWidth(String width) {
    	this.chartContainer.setWidth(width);
    	this.resizableContainer.setWidth(width);
    }

    @Override
    public HandlerRegistration addAttachedChartHandler(AttachedChartHandler handler) {
	   return this.addHandler(handler, AttachedChartEvent.getType());
    }

    /**
     * @return the chartOptionner
     */
    protected final ChartOptioner getChartOptionner() {
	   return this.chartOptionner;
    }

    protected final void importOptionner(ChartOptioner chartOptionner) {
	   this.chartOptionner = chartOptionner;
    }
}