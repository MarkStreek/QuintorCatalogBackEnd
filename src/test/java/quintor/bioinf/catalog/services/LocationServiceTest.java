//package quintor.bioinf.catalog.services;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest(properties = {
//        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
//        "spring.datasource.driverClassName=org.h2.Driver",
//        "spring.datasource.username=sa",
//        "spring.datasource.password=",
//        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
//})
//class LocationServiceTest {
//
//    @Autowired
//    private LocationService locationService;
//
//    @Test
//    void createLocation_ValidCityAndAddress_ReturnsLocation() {
////        Location location = locationService.createLocation("Basement","City", "Street 123");
////        assertNotNull(location);
////        assertEquals("City", location.getCity());
////        assertEquals("Street 123", location.getAddress());
//        System.out.println("test");
//    }
////
////    @Test
////    void createLocation_EmptyCity_ThrowsException() {
////        assertThrows(IllegalArgumentException.class, () -> {
////            locationService.createLocation("Server Room","", "Street 123");
////        });
////    }
////
////    @Test
////    void createLocation_EmptyAddress_ThrowsException() {
////        assertThrows(IllegalArgumentException.class, () -> {
////            locationService.createLocation("Server Room","City", "");
////        });
////    }
////
////    @Test
////    void createLocation_InvalidAddress_ThrowsException() {
////        assertThrows(IllegalArgumentException.class, () -> {
////            locationService.createLocation("", "", "");
////        });
////    }
//}