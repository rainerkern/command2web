package systemkern.receiver

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.Duration


@Component
//@ConfigurationPropertiesBinding
internal class DurationConverter : Converter<String, Duration> {

    override fun convert(source: String): Duration =
        Duration.parse(source)

}
