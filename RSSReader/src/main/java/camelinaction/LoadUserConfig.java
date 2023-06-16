package camelinaction;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

// load configuration made by user from config.xml
public class LoadUserConfig {

	public UserConfig load() throws Exception {
		UserConfig userConfig = null;
		try {
			// use JAXB to map config.xml file to java object of class UserConfig
			File xmlFile = new File("data/inbox/config.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(UserConfig.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			userConfig = (UserConfig) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return userConfig;

	}

}
