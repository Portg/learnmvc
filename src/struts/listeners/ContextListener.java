package struts.listeners;


import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import struts.action.ActionMapping;
import struts.config.parser.XmlParser;

public final class ContextListener implements  ServletContextListener {

    private static final String actionMap = "actionMap";

    private static final String config = "/WEB-INF/struts-config.xml";

    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;

    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        log("contextDestroyed()");
        this.context = null;

    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        this.context = event.getServletContext();
        String xmlFile = context.getInitParameter("config");
        if(xmlFile == null || xmlFile.equals("")){
            xmlFile = config;
        }
        String realPath = context.getRealPath("\\");
        realPath += xmlFile;
        Map<String, ActionMapping> map = XmlParser.parse(realPath);
        context.setAttribute(actionMap, map);
        log("contextInitialized()");

    }

    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
    private void log(String message) {

        if (context != null)
            context.log("ContextListener: " + message);
        else
            System.out.println("ContextListener: " + message);

    }

}
