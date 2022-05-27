module data.test {
    requires data.core;
    requires org.junit.jupiter.api;
    requires com.google.gson;
    opens com.ejlchina.data.test;
    exports com.ejlchina.data.test;
}