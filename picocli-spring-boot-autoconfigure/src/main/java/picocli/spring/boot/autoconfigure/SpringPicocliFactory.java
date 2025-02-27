package picocli.spring.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

/**
 * @author Thibaud Leprêtre
 */
public class SpringPicocliFactory implements CommandLine.IFactory {
    private static final Logger logger = LoggerFactory.getLogger(SpringPicocliFactory.class);

    private final ApplicationContext applicationContext;

    public SpringPicocliFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <K> K create(Class<K> clazz) throws Exception {
        try {
            return getBeanOrCreate(clazz);
        } catch (Exception e) {
            logger.warn("unable to get bean of class {}, use default Picocli factory creation", clazz);
            return CommandLine.defaultFactory().create(clazz);
        }
    }

    private <K> K getBeanOrCreate(Class<K> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            return applicationContext.getAutowireCapableBeanFactory().createBean(clazz);
        }
    }
}
