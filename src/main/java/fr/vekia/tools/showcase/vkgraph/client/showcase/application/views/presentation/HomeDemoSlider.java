package fr.vekia.tools.showcase.vkgraph.client.showcase.application.views.presentation;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vekia.tools.showcase.vkgraph.client.showcase.application.services.CodeServiceUtil;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.ui.Slideshow;
import fr.vekia.tools.showcase.vkgraph.client.showcase.demonstration.items.interactives.PyramidChartInteractiveWidget;
import fr.vekia.tools.showcase.vkgraph.client.showcase.demonstration.screens.interactives.AreaChartInteractiveWidgetScreen;
import fr.vekia.tools.showcase.vkgraph.client.showcase.demonstration.screens.simple.SimplePlotScreen;

/**
 * Image slider demo for home page.
 * 
 * @author svandecappelle
 * @version 5.0.0
 */
public class HomeDemoSlider extends SimplePanel {

    interface ImgRessource extends ClientBundle {
        @ImageOptions(width = 1000, height = 650)
        ImageResource screenshotOne();

        @ImageOptions(width = 1000, height = 650)
        ImageResource screenshotTwo();

        final static class Util {
            private static ImgRessource instance;

            /**
             * Default constructor
             * 
             */
            private Util() {
            }

            static final ImgRessource getInstance() {
                if (instance == null) {
                    instance = GWT.create(ImgRessource.class);
                }
                return instance;
            }
        }
    }

    public HomeDemoSlider() {

        CallbackEventController<ConsoleSlideshowCallBack, IsWidget> callBackEventController = new CallbackEventController<ConsoleSlideshowCallBack, IsWidget>() {

            @Override
            public void onSuccess(List<IsWidget> result) {
                final Slideshow slideshow = new Slideshow(Window.getClientWidth() / 2, Window.getClientHeight() / 2);
                Image screenshotOne = new Image(ImgRessource.Util.getInstance().screenshotOne());
                Image screenshotTwo = new Image(ImgRessource.Util.getInstance().screenshotTwo());
                Window.addResizeHandler(new ResizeHandler() {

                    @Override
                    public void onResize(ResizeEvent event) {
                        slideshow.setSize(Window.getClientWidth() / 2 + "px", Window.getClientHeight() / 2 + "px");
                        slideshow.setSizeContainerPx(Window.getClientWidth() / 2, Window.getClientHeight() / 2);
                        slideshow.redraw();
                    }
                });
                slideshow.add(screenshotOne);
                slideshow.add(screenshotTwo);

                for (IsWidget isWidget : result) {
                    slideshow.add((Widget) isWidget);
                }

                setWidget(slideshow);
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("not able to retreive code due to: ", caught);
            }
        };
        final ConsoleSlideshowCallBack generalPlot = new ConsoleSlideshowCallBack(callBackEventController);
        final ConsoleSlideshowCallBack areaPlot = new ConsoleSlideshowCallBack(callBackEventController);
        final ConsoleSlideshowCallBack pyramidPlot = new ConsoleSlideshowCallBack(callBackEventController);
        callBackEventController.addTraitment(new CodingGetShowcaseSlideshowCommand(generalPlot, SimplePlotScreen.class));
        callBackEventController.addTraitment(new CodingGetShowcaseSlideshowCommand(areaPlot, AreaChartInteractiveWidgetScreen.class));
        callBackEventController.addTraitment(new CodingGetShowcaseSlideshowCommand(pyramidPlot, PyramidChartInteractiveWidget.class));

        callBackEventController.execute();
    }

    static class CodingGetShowcaseSlideshowCommand implements Command {

        private Class<?> classBinded;
        private ConsoleSlideshowCallBack callBack;

        /**
         * Default constructor
         * 
         */
        public CodingGetShowcaseSlideshowCommand(ConsoleSlideshowCallBack callBack, Class<?> classBinded) {
            this.classBinded = classBinded;
            this.callBack = callBack;
        }

        @Override
        public void execute() {
            CodeServiceUtil.getCode(this.classBinded.getName(), callBack);
        }

    }
}