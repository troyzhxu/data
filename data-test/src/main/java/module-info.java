module data.test {
    requires data.core;
    requires org.junit.jupiter.api;
    opens com.ejlchina.data.test;
    exports com.ejlchina.data.test;
}