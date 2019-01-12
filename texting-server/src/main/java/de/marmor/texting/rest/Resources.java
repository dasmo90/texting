package de.marmor.texting.rest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

@Controller
public class Resources {

    private static File[] economySet() {
        ClassLoader loader = Resources.class.getClassLoader();
        URL url = loader.getResource("cards");
        String path = url.getPath();
        return new File(path).listFiles();
    }

    @RequestMapping("cards/{cardId}")
    public void card(@PathVariable("cardId") int cardId, HttpServletResponse response) throws IOException {
        File[] economeysets = economySet();
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(new FileInputStream(economeysets[cardId]), outputStream);
        outputStream.close();
    }
}
