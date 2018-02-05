package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("77.56.48.207");
    assertEquals(geoIP.getCountryCode(), "CHE");
  }

  @Test
  public void testInvalidIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("77.56.48.2x");
    assertEquals(geoIP.getCountryCode(), "CHE");
  }
}
