package di;

import datasource.model.Storage;
import datasource.repository.Repository;
import datasource.repository.RepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfiguration {

    @Bean
    Storage getStorage()
    {
        return new Storage();
    }

    @Bean
    Repository getRepository()
    {
        return new Repository();
    }
    @Bean
    RepositoryService getRepositoryService(Repository repository)
    {
        return new RepositoryService(repository);
    }

}
