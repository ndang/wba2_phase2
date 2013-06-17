//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.17 at 06:09:52 PM CEST 
//


package app;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _User_QNAME = new QName("", "user");
    private final static QName _ThemeAllgemeinesKategorienKategorie_QNAME = new QName("", "kategorie");
    private final static QName _ThemeAllgemeinesGenresGenre_QNAME = new QName("", "genre");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Theme }
     * 
     */
    public Theme createTheme() {
        return new Theme();
    }

    /**
     * Create an instance of {@link Beschreibung }
     * 
     */
    public Beschreibung createBeschreibung() {
        return new Beschreibung();
    }

    /**
     * Create an instance of {@link Theme.Interaktion }
     * 
     */
    public Theme.Interaktion createThemeInteraktion() {
        return new Theme.Interaktion();
    }

    /**
     * Create an instance of {@link Theme.Interaktion.Kommentare }
     * 
     */
    public Theme.Interaktion.Kommentare createThemeInteraktionKommentare() {
        return new Theme.Interaktion.Kommentare();
    }

    /**
     * Create an instance of {@link Theme.Module }
     * 
     */
    public Theme.Module createThemeModule() {
        return new Theme.Module();
    }

    /**
     * Create an instance of {@link Theme.Module.Musik }
     * 
     */
    public Theme.Module.Musik createThemeModuleMusik() {
        return new Theme.Module.Musik();
    }

    /**
     * Create an instance of {@link Theme.Allgemeines }
     * 
     */
    public Theme.Allgemeines createThemeAllgemeines() {
        return new Theme.Allgemeines();
    }

    /**
     * Create an instance of {@link Themes }
     * 
     */
    public Themes createThemes() {
        return new Themes();
    }

    /**
     * Create an instance of {@link Rezept }
     * 
     */
    public Rezept createRezept() {
        return new Rezept();
    }

    /**
     * Create an instance of {@link Beschreibung.Text }
     * 
     */
    public Beschreibung.Text createBeschreibungText() {
        return new Beschreibung.Text();
    }

    /**
     * Create an instance of {@link Theme.Interaktion.Abonennten }
     * 
     */
    public Theme.Interaktion.Abonennten createThemeInteraktionAbonennten() {
        return new Theme.Interaktion.Abonennten();
    }

    /**
     * Create an instance of {@link Theme.Interaktion.Kommentare.Kommentar }
     * 
     */
    public Theme.Interaktion.Kommentare.Kommentar createThemeInteraktionKommentareKommentar() {
        return new Theme.Interaktion.Kommentare.Kommentar();
    }

    /**
     * Create an instance of {@link Theme.Module.Dekoration }
     * 
     */
    public Theme.Module.Dekoration createThemeModuleDekoration() {
        return new Theme.Module.Dekoration();
    }

    /**
     * Create an instance of {@link Theme.Module.Outfits }
     * 
     */
    public Theme.Module.Outfits createThemeModuleOutfits() {
        return new Theme.Module.Outfits();
    }

    /**
     * Create an instance of {@link Theme.Module.Catering }
     * 
     */
    public Theme.Module.Catering createThemeModuleCatering() {
        return new Theme.Module.Catering();
    }

    /**
     * Create an instance of {@link Theme.Module.Locations }
     * 
     */
    public Theme.Module.Locations createThemeModuleLocations() {
        return new Theme.Module.Locations();
    }

    /**
     * Create an instance of {@link Theme.Module.Musik.Song }
     * 
     */
    public Theme.Module.Musik.Song createThemeModuleMusikSong() {
        return new Theme.Module.Musik.Song();
    }

    /**
     * Create an instance of {@link Theme.Allgemeines.Genres }
     * 
     */
    public Theme.Allgemeines.Genres createThemeAllgemeinesGenres() {
        return new Theme.Allgemeines.Genres();
    }

    /**
     * Create an instance of {@link Theme.Allgemeines.Kategorien }
     * 
     */
    public Theme.Allgemeines.Kategorien createThemeAllgemeinesKategorien() {
        return new Theme.Allgemeines.Kategorien();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "user")
    public JAXBElement<String> createUser(String value) {
        return new JAXBElement<String>(_User_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "kategorie", scope = Theme.Allgemeines.Kategorien.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    public JAXBElement<String> createThemeAllgemeinesKategorienKategorie(String value) {
        return new JAXBElement<String>(_ThemeAllgemeinesKategorienKategorie_QNAME, String.class, Theme.Allgemeines.Kategorien.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "genre", scope = Theme.Allgemeines.Genres.class)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    public JAXBElement<String> createThemeAllgemeinesGenresGenre(String value) {
        return new JAXBElement<String>(_ThemeAllgemeinesGenresGenre_QNAME, String.class, Theme.Allgemeines.Genres.class, value);
    }

}
