/*
 * File: $URL$
 * $Id$
 * Copyright: Vekia
 *
 * Last change:
 * $Date$
 * $Author$
 */
package fr.vekia.tools.showcase.vkgraph.client.showcase.application.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author svandecappelle
 * @since Nov 22, 2012. VklGraph version 1.2
 * @version 2.1
 */
public class JQueryDialog extends SimplePanel implements ResizeHandler, HasResizeHandlers {

    private static final int HEADER_HEIGHT = 35;

    private SimplePanel innerpopup;
    private SimplePanel scrollableContent = new SimplePanel();
    private String id;

    private int height;
    private int width;

    private boolean autoSize;

    private boolean sized;

    /**
     * Default constructor
     * 
     */
    public JQueryDialog(String title) {
        this(title, true);
    }

    /**
     * Default constructor
     * 
     */
    public JQueryDialog(String title, boolean autoSize) {
        this.id = DOM.createUniqueId();
        this.innerpopup = new SimplePanel();
        this.scrollableContent = new SimplePanel();

        this.innerpopup.add(this.scrollableContent);
        this.innerpopup.setTitle(title);
        this.innerpopup.getElement().setId(id);
        this.autoSize = autoSize;

        super.add(this.innerpopup);
    }

    public void setPreferedSize(int height, int width) {
        this.sized = true;
        this.height = height;
        this.width = width;
    }

    /**
     * 
     */
    public void show() {
        RootLayoutPanel.get().insert(this, 0);

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                if (!sized) {
                    if (autoSize) {
                        height = scrollableContent.getOffsetHeight() + HEADER_HEIGHT;
                        width = scrollableContent.getOffsetWidth();
                    } else {
                        height = Window.getClientHeight() / 2;
                        width = Window.getClientWidth() / 3;
                    }
                }
                open(id, height, width);
                RootLayoutPanel.get().remove(JQueryDialog.this);
            }
        });
    };

    /**
     * 
     */
    public void close() {
        close(id);
    };

    public void setCode(String code) {
        this.scrollableContent.getElement().setInnerHTML(code);
    }

    /**
     * 
     */
    // @formatter:off
    private native void open(String id, int height, int width) /*-{
       $wnd.jQuery("#" + id).dialog({
           show : "slide",
           hide : "explode",
           height : height,
           width : width
           
       });
    }-*/;
    
    private native void close(String id) /*-{
        $wnd.jQuery("#" + id).dialog("close");
     }-*/;
    // @formatter:on

    @Override
    public void onResize(ResizeEvent event) {
        // this.height = this.innerpopup.getOffsetHeight();
        // this.width = this.innerpopup.getOffsetWidth();
        // this.scrollableContent.getElement().getStyle().setPropertyPx("maxHeight", height);
        // this.scrollableContent.getElement().getStyle().setPropertyPx("maxWidth", width);
    }

    @Override
    public HandlerRegistration addResizeHandler(ResizeHandler handler) {
        return addHandler(handler, ResizeEvent.getType());
    }

    public void setContent(IsWidget widget) {
        this.scrollableContent.setWidget(widget);
    }

}