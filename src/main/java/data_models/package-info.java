@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalTime.class,
                value=LocalTimeXmlAdapter.class),
        @XmlJavaTypeAdapter(type = Duration.class , value = DurationXmlAdapter.class)
})


package data_models;

import com.migesok.jaxb.adapter.javatime.DurationXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.Duration;
import java.time.LocalTime;

