/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

/**
 *
 * @author villermt
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
  // Définition de la propriété brand.
  private final StringProperty brand = new SimpleStringProperty(this, "brand", null);

  // Getter.
  public final String getBrand() {
    return brand.get();
  }

  // Setter.
  public final void setBrand(final String value) {
    brand.set(value);
  }

  // Accès à la propriété.
  public final StringProperty brandProperty() {
    return brand;
  }
  
  @Override
public String toString() {
  return getBrand();
}

}
