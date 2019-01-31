package edu.aem.training.eventhandler;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component
@Service(value={JobConsumer.class})
@Property(name=JobConsumer.PROPERTY_TOPICS, value="edu/aem/training/eventhandler/propertyremoval")
public class PropertyRemovalJobConsumer implements JobConsumer {

    private final String REM_PATH = "/var/log/removedProperties";
    private final String LOG_PATH = "var/log";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference
    private SlingRepository repository;

    public JobResult process(Job job) {

        log.info("Start process job: " + job.getId());
        log.info("Removal property name: " + job.getProperty("name"));
        log.info("Removal property path: " + job.getProperty("path"));

        try {
            Session session = repository.loginAdministrative(null);
            // Generate unique id of new node
            String id = job.getId().replace('/', '_');
            // Create new node
            Node node = JcrUtils.getOrCreateByPath(REM_PATH+ "/" + id, "sling:OrderedFolder",
                    "nt:unstructured", session, true);
            log.info("Node created: " + node.getPath());
            node.setProperty("propertyName", (String) job.getProperty("name"));
            node.setProperty("propertyPath", (String) job.getProperty("path"));
            session.save();
            session.logout();
        } catch (Exception e) {
            log.info("ERROR: " + e.getMessage());
        }

        return JobResult.OK;
    }
}
