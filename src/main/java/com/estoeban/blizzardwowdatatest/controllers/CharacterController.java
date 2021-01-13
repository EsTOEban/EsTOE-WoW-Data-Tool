package com.estoeban.blizzardwowdatatest.controllers;

import com.estoeban.blizzardwowdatatest.config.AppConfig;
import com.estoeban.blizzardwowdatatest.models.Character;
import com.estoeban.blizzardwowdatatest.service.SignatureImageService;
import com.estoeban.blizzardwowdatatest.service.WowCharacterInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController(value = "character")
@RequestMapping("character")
public class CharacterController {

    private final AppConfig appConfig;
    private final SignatureImageService signatureImageService;
    private final WowCharacterInformationService wowCharacterInformationService;

    @Autowired
    CharacterController(SignatureImageService signatureImageService, AppConfig appConfig,
        WowCharacterInformationService wowCharacterInformationService) {
        this.signatureImageService = signatureImageService;
        this.appConfig = appConfig;
        this.wowCharacterInformationService = wowCharacterInformationService;
    }

    @GetMapping()
    public ResponseEntity<Character> getCharacter(
        @RequestParam String characterName,
        @RequestParam String realmName
    ) throws IOException, URISyntaxException {
        Character character = new Character();
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            character = wowCharacterInformationService.getCharacterInformation(characterName, realmName);
        } catch (Exception e) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(character, httpStatus);
    }

    @GetMapping("/pvp")
    public Character getCharacterPvpInfo(
            @RequestParam String characterName,
            @RequestParam String realmName
        ) {
        Character character = new Character();
        character.setName(characterName);
        return character;
    }

    @ResponseBody
    @GetMapping(path = "/signature", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getSignatureImage(
            @RequestParam(required = true) final String characterName,
            @RequestParam(required = true) final String realmName
    ) throws IOException, URISyntaxException {
        ImageOutputStream imageOutputStream = null;
        ImageWriter writer = null;
        try {
            // We need a byte array for returning our image.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Get a custom writer that uses a custom compression quality for our resulting jpeg
            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
            // We've been assuming RGB in the app, so let's keep that up
            jpegParams.setDestinationType(ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB));
            // Set the custom compression quality, and make sure that we use it
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(appConfig.getCompressionQuality());

            // Make an ImageOutputStream that accepts our ByteArrayOutputStream, without needing a lot of additional resources
            imageOutputStream = new MemoryCacheImageOutputStream(baos);

            // prep the writer to make a jpg type to our ImageOutputStream
            writer = ImageIO.getImageWritersByFormatName("jpg").next();
            writer.setOutput(imageOutputStream);

            // Use the writer to output the contents of the image into our ByteArrayOutputStream via the ImageOutputStream
            writer.write(null, new IIOImage(signatureImageService.generateSignature(characterName, realmName).getData(), null, null), jpegParams);

            // Return the resulting byte array
            return baos.toByteArray();
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            // Clean up after ourselves
            if(imageOutputStream != null){
                imageOutputStream.close();
            }
            if(writer != null){
                writer.dispose();
            }
        }
    }
}
