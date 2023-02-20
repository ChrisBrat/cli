package za.co.ckb.cli.command;

import picocli.CommandLine;
import za.co.ckb.cli.io.ConfigProperties;

import java.util.Properties;

@CommandLine.Command(
        name = "config"
)
public class ConfigCommand implements Runnable {

    @CommandLine.Option(names = {"-u", "--update"})
    private boolean updateConfigs;

    @Override
    public void run() {

        Properties properties = ConfigProperties.getInstance().getProperties();

        if (!updateConfigs){
            for (Object key : properties.keySet()) {
                System.err.println(key+"="+properties.getProperty(key.toString(), ""));
            }
        } else {

            for (ConfigProperties.PROPERTY_NAMES propertywithDescription : ConfigProperties.PROPERTY_NAMES.values()) {
                setProperty(properties, propertywithDescription.getText(), propertywithDescription.getDescription());
            }

            System.err.println("\nPersist updates ? [yY]");
            String persistUpdate = System.console().readLine();

            if ("Y".equalsIgnoreCase(persistUpdate) || "".equalsIgnoreCase(persistUpdate)){
                ConfigProperties.getInstance().writeProperties(properties);
            }
        }
    }

    private void setProperty(Properties properties, String propertyName, String propertyDescription) {
        String propertyValue = properties.getProperty(propertyName);
        propertyValue = (propertyValue == null) ? "" : propertyValue;
        System.out.printf("%s (%s) [%s] : ", propertyDescription, propertyName, propertyValue);

        String propertyValueUpdate = System.console().readLine();
        if (propertyValueUpdate.length() > 0){
            propertyValue = propertyValueUpdate.trim();
        }

        properties.setProperty(propertyName, propertyValue);
    }
}
