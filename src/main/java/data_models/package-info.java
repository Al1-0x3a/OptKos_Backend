@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalTime.class,
                value=LocalTimeXmlAdapter.class),
        @XmlJavaTypeAdapter(type = Duration.class , value = DurationXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalDate.class , value = LocalDateXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalDateTime.class , value = LocalTimeXmlAdapter.class),
})


package data_models;

import com.migesok.jaxb.adapter.javatime.DurationXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalDateXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

