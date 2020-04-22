package org.flomen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileConverter {

    public static final String PEOPLE_START_TAG = "<people>";

    public static final String PERSON_START_TAG = " <person>";
    public static final String FIRST_NAME_START_TAG = "  <firstname>";
    public static final String FIRST_NAME_END_TAG = "</firstname>";
    public static final String LAST_NAME_START_TAG = "  <lastname>";
    public static final String LAST_NAME_END_TAG = "</lastname>";
    public static final String PERSON_END_TAG = " </person>";

    public static final String PHONE_START_TAG = " <phone>";
    public static final String MOBILE_START_TAG = "  <mobile>";
    public static final String MOBILE_END_TAG = "</mobile>";
    public static final String HOME_PHONE_START_TAG = "  <homePhone>";
    public static final String HOME_PHONE_END_TAG = "</homePhone>";
    public static final String PHONE_END_TAG = " </phone>";

    public static final String ADDRESS_START_TAG = " <address>";
    public static final String STREET_START_TAG = "  <street>";
    public static final String STREET_END_TAG = "</street>";
    public static final String CITY_START_TAG = "  <city>";
    public static final String CITY_END_TAG = "</city>";
    public static final String POSTCODE_START_TAG = "  <postcode>";
    public static final String POSTCODE_END_TAG = "</postcode>";
    public static final String ADDRESS_END_TAG = " </address>";
    public static final String FAMILY_START_TAG = " <family>";
    public static final String NAME_START_TAG = "  <name>";
    public static final String NAME_END_TAG = "</name>";
    public static final String BORN_START_TAG = "  <born>";
    public static final String BORN_END_TAG = "</born>";
    public static final String FAMILY_END_TAG = " </family>";

    public static final String PEOPLE_END_TAG = "</people>";

    public static boolean fileConverter(String selectedLocation, String saveLocation) {

        return writeToFile(convertTxtToXml(selectedLocation), saveLocation);

    }

    private static String convertTxtToXml(String exampleFileTxt) {

        List<String> lines;
        StringBuffer xmlFile = new StringBuffer(PEOPLE_START_TAG);
        String[] line = null;
        String previousLinePrefix = "";
        String currentLinePrefix = "";


        lines = readFile(exampleFileTxt);

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i).split("\\|");
            currentLinePrefix = line[0];

            if (i == 0) {
                xmlFile.append("\n" + formatLine(line));
            } else {
                if (isPersonAfterFamily(previousLinePrefix, currentLinePrefix)) {
                    System.out.println("Person can't come after Family!");
                } else {
                    xmlFile.append("\n" + formatLine(line));
                }
            }
            previousLinePrefix = line[0];
        }

        xmlFile.append(PEOPLE_END_TAG);
        System.out.println(xmlFile.toString());
        return xmlFile.toString();
    }

    private static List readFile(String file) {
        List<String> lines = new ArrayList();
        try {
            Scanner fileReader = new Scanner(new File(file));
            while (fileReader.hasNextLine()) {
                try {
                    lines.add(fileReader.nextLine());
                } catch (NoSuchElementException e) {
                    System.out.println("Extra bland line!");
                }
            }

            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find this file!");
        }

        return lines;
    }

    private static boolean isPersonAfterFamily(String previousLinePrefix, String currentLinePrefix) {
        boolean personIsAfterFamily = false;
        if (currentLinePrefix.equals("P")) {
            if (previousLinePrefix.equals("F")) {
                personIsAfterFamily = true;
            }
        }

        return personIsAfterFamily;
    }

    private static String formatLine(String... line) {
        String lineInXml = "";

        switch (line[0]) {
            case "P":
                try {
                    String firstName = line[1];
                    String lastName = line[2];

                    lineInXml = createLinePerson(firstName, lastName);
                } catch (Exception e) {
                    System.out.println("Error line is incorrect!");
                }
                break;
            case "T":
                try {
                    String mobile = line[1];
                    String homePhone = line[2];

                    lineInXml = createLinePhone(mobile, homePhone);
                } catch (Exception e) {
                    System.out.println("Error line is incorrect!");
                }
                break;
            case "A":
                try {
                    String street = line[1];
                    String city = line[2];
                    String postcode = line[3];

                    lineInXml = createLineAddress(street, city, postcode);
                } catch (Exception e) {
                    System.out.println("Error line is incorrect!");
                }
                break;
            case "F":
                try {
                    String name = line[1];
                    String born = line[2];

                    lineInXml = createLineFamily(name, born);
                } catch (Exception e) {
                    System.out.println("Error line is incorrect!");
                }
                break;
            default:
                System.out.println("No match");
        }
        return lineInXml;
    }

    private static String createLinePerson(String firstName, String lastName) {
        StringBuffer lineInXml = new StringBuffer();

        return lineInXml.append(PERSON_START_TAG)
                .append(System.lineSeparator())
                .append(FIRST_NAME_START_TAG + firstName + FIRST_NAME_END_TAG)
                .append(System.lineSeparator())
                .append(LAST_NAME_START_TAG + lastName + LAST_NAME_END_TAG)
                .append(System.lineSeparator())
                .append(PERSON_END_TAG).toString();
    }

    private static String createLinePhone(String mobile, String homePhone) {
        StringBuffer lineInXml = new StringBuffer();

        return lineInXml.append(PHONE_START_TAG)
                .append(System.lineSeparator())
                .append(MOBILE_START_TAG + mobile + MOBILE_END_TAG)
                .append(System.lineSeparator())
                .append(HOME_PHONE_START_TAG + homePhone + HOME_PHONE_END_TAG)
                .append(System.lineSeparator())
                .append(PHONE_END_TAG).toString();
    }

    private static String createLineAddress(String street, String city, String postcode) {
        StringBuffer lineInXml = new StringBuffer();

        return lineInXml.append(ADDRESS_START_TAG)
                .append(System.lineSeparator())
                .append(STREET_START_TAG + street + STREET_END_TAG)
                .append(System.lineSeparator())
                .append(CITY_START_TAG + city + CITY_END_TAG)
                .append(System.lineSeparator())
                .append(POSTCODE_START_TAG + postcode + POSTCODE_END_TAG)
                .append(System.lineSeparator())
                .append(ADDRESS_END_TAG).toString();
    }

    private static String createLineFamily(String name, String born) {
        StringBuffer lineInXml = new StringBuffer();

        return lineInXml.append(FAMILY_START_TAG)
                .append(System.lineSeparator())
                .append(NAME_START_TAG + name + NAME_END_TAG)
                .append(System.lineSeparator())
                .append(BORN_START_TAG + born + BORN_END_TAG)
                .append(System.lineSeparator())
                .append(FAMILY_END_TAG).toString();
    }

    private static boolean writeToFile(String file, String saveLocation) {
        boolean isCompleted = false;
        try {
            FileWriter writer = new FileWriter(saveLocation);
            writer.write(file);
            writer.close();
            isCompleted = true;
        } catch (IOException e) {
            System.out.println("This file already exists!");
        }
        return isCompleted;
    }

}