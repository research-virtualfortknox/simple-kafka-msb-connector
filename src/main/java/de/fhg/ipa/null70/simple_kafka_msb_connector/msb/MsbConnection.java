package de.fhg.ipa.null70.simple_kafka_msb_connector.msb;

import de.fhg.ipa.vfk.msb.client.api.Application;
import de.fhg.ipa.vfk.msb.client.websocket.MsbClient;
import de.fhg.ipa.vfk.msb.client.websocket.MsbClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */
@Component
public class MsbConnection {

	private final static Logger LOG = LoggerFactory.getLogger(MsbConnection.class);

	@Value("${msb.truststore}")
	private String truststore;
	@Value("${msb.url}")
	private String url;

	@Value("${app.uuid}")
	private String uuid;
	@Value("${app.name}")
	private String name;
	@Value("${app.token}")
	private String token;
	@Value("${app.description}")
	private String description;

	@Autowired
	private MsbFunctions msbFunctions;

	private MsbClientHandler msbClientHandler;

	@PostConstruct
	public void init() {
		printProperties();
		startConnection();
	}

	/**
	 * This method opens a connection to the configured MSB instance and registers the implemented service.
	 */
	private void startConnection() {
		MsbClient msbClient = new MsbClient.Builder().url(url).build();
		Future<MsbClientHandler> future = msbClient.connect();

		try {
			MsbClientHandler handler = future.get();
			Application msbApplication = new Application(uuid, name, description, token);
			String functionsPackageName = MsbFunctions.class.getPackage().getName();
			handler.register(msbApplication, new Object[]{msbFunctions}, functionsPackageName);
			setMsbClientHandler(handler);
		} catch (InterruptedException | ExecutionException e) {
			// …
			LOG.warn("Exception during msb client execution: ",e);
		} catch (IOException e){
			// …
			LOG.warn("Exception during msb client execution: ",e);
		}
	}

	private void setMsbClientHandler(MsbClientHandler clientHandler) {
		this.msbClientHandler = clientHandler;
	}

	public MsbClientHandler getMsbClientHandler() {
		while(msbClientHandler == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOG.warn("InterruptedException: ",e);
			}
		}
		return this.msbClientHandler;
	}

	private void printProperties() {
		LOG.info(this.toString());
	}

	@Override
	public String toString() {
		return "MsbConnection{" +
				"uuid='" + uuid + '\'' +
				", name='" + name + '\'' +
				", token='" + token + '\'' +
				", description='" + description + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
