package net.dgrigore.mem.analyzer.doodle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doodle")
public class DoodleResource {

    private static final Logger LOG = LoggerFactory.getLogger(DoodleResource.class);

    private static final int COUNT = 400;

    private static final int PAGE_SIZE = 50;

    private final DoodleDao dao;

    @Autowired
    public DoodleResource(DoodleDao dao) {
        super();
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void load() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("img.jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileCopyUtils.copy(in, out);
        LOG.info("Loaded {} bytes.", out.toByteArray().length);

        List<Doodle> doodleList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            Doodle d = new Doodle();
            d.setData(out.toByteArray());
            doodleList.add(d);
        }
        this.dao.save(doodleList);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Doodle> get() {
        long count = this.dao.count();
        long pages = count / PAGE_SIZE;
        LOG.info("Found {} doodles and {} pages", count, pages);
        for (int page = 0; page < pages; page++) {
            List<Doodle> doodles = this.dao.find(page * PAGE_SIZE, PAGE_SIZE);
            LOG.info("Loaded page {} ", page);
            this.dao.detach(doodles);
        }
        LOG.info("Loaded {} doodles.", count);
        return Collections.emptyList();
    }
}
