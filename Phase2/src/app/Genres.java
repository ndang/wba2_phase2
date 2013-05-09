//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.09 at 06:36:26 PM CEST 
//


package app;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="genre" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="genre_titel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="genre_beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="genre_id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "genre"
})
@XmlRootElement(name = "genres")
public class Genres {

    @XmlElement(required = true)
    protected List<Genres.Genre> genre;

    /**
     * Gets the value of the genre property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genre property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenre().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Genres.Genre }
     * 
     * 
     */
    public List<Genres.Genre> getGenre() {
        if (genre == null) {
            genre = new ArrayList<Genres.Genre>();
        }
        return this.genre;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="genre_titel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="genre_beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *       &lt;attribute name="genre_id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "genreTitel",
        "genreBeschreibung"
    })
    public static class Genre {

        @XmlElement(name = "genre_titel", required = true)
        protected String genreTitel;
        @XmlElement(name = "genre_beschreibung", required = true)
        protected String genreBeschreibung;
        @XmlAttribute(name = "genre_id", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String genreId;

        /**
         * Gets the value of the genreTitel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGenreTitel() {
            return genreTitel;
        }

        /**
         * Sets the value of the genreTitel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGenreTitel(String value) {
            this.genreTitel = value;
        }

        /**
         * Gets the value of the genreBeschreibung property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGenreBeschreibung() {
            return genreBeschreibung;
        }

        /**
         * Sets the value of the genreBeschreibung property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGenreBeschreibung(String value) {
            this.genreBeschreibung = value;
        }

        /**
         * Gets the value of the genreId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getGenreId() {
            return genreId;
        }

        /**
         * Sets the value of the genreId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setGenreId(String value) {
            this.genreId = value;
        }

    }

}
