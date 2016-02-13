package io.pivotal.gemfire.cache.client;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gemstone.gemfire.cache.DataPolicy;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

/**
 * The GemFireClientCacheXmlTest class...
 *
 * @author John Blum
 * @since 1.0.0
 */
public class GemFireClientCacheXmlTest {

	private static ClientCache clientCache;

	private static Region<Long, Long> squareRoots;

	@BeforeClass
	public static void setupGemFire() {
		clientCache = new ClientCacheFactory(gemfireProperties()).set("cache-xml-file", "client-cache.xml").create();
		squareRoots = clientCache.getRegion("SquareRoots");
	}

	static Properties gemfireProperties() {
		Properties gemfireProperties = new Properties();
		gemfireProperties.setProperty("log-level", System.getProperty("gemfire.log-level", "config"));
		return gemfireProperties;
	}

	@AfterClass
	public static void tearDownGemFire() {
		if (clientCache != null) {
			clientCache.close(false);
		}
	}

	@Before
	public void setup() {
		assertThat(squareRoots, is(notNullValue()));
		assertThat(squareRoots.getName(), is(equalTo("SquareRoots")));
		assertThat(squareRoots.getFullPath(), is(equalTo(String.format("%1$sSquareRoots", Region.SEPARATOR))));
		assertThat(squareRoots.getAttributes(), is(notNullValue()));
		assertThat(squareRoots.getAttributes().getDataPolicy(), is(equalTo(DataPolicy.EMPTY)));
		assertThat(squareRoots.getAttributes().getPoolName(), is(equalTo("serverPool")));
	}

	@Test
	public void computeSquareRoots() {
		assertThat(squareRoots.get(100l), is(equalTo(10l)));
		assertThat(squareRoots.get(81l), is(equalTo(9l)));
		assertThat(squareRoots.get(64l), is(equalTo(8l)));
		assertThat(squareRoots.get(49l), is(equalTo(7l)));
		assertThat(squareRoots.get(36l), is(equalTo(6l)));
		assertThat(squareRoots.get(25l), is(equalTo(5l)));
		assertThat(squareRoots.get(16l), is(equalTo(4l)));
		assertThat(squareRoots.get(9l), is(equalTo(3l)));
		assertThat(squareRoots.get(4l), is(equalTo(2l)));
		assertThat(squareRoots.get(1l), is(equalTo(1l)));
		assertThat(squareRoots.get(0l), is(equalTo(0l)));
	}

}
