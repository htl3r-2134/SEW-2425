/* -*- java -*-
# =========================================================================== #
#                                                                             #
#                         Copyright (C) KNAPP AG                              #
#                                                                             #
#       The copyright to the computer program(s) herein is the property       #
#       of Knapp.  The program(s) may be used   and/or copied only with       #
#       the  written permission of  Knapp  or in  accordance  with  the       #
#       terms and conditions stipulated in the agreement/contract under       #
#       which the program(s) have been supplied.                              #
#                                                                             #
# =========================================================================== #
*/

package com.knapp.codingcontest.solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.knapp.codingcontest.data.InputData;
import com.knapp.codingcontest.data.Institute;
import com.knapp.codingcontest.data.Location;
import com.knapp.codingcontest.data.Order;
import com.knapp.codingcontest.data.Product;
import com.knapp.codingcontest.warehouse.Robot;
import com.knapp.codingcontest.warehouse.Storage;
import com.knapp.codingcontest.warehouse.Warehouse;
import com.knapp.codingcontest.warehouse.WarehouseInfo;
import com.knapp.codingcontest.warehouse.ex.NoSuchLocationException;


public class Solution {

  public String getParticipantName() {
    return "Julian";
  }

  public Institute getParticipantInstitution() {
    return Institute.HTL_Rennweg_Wien; // TODO: return the Id of your institute - please refer to the hand-out
  }

  // ----------------------------------------------------------------------------

  protected final InputData input;
  protected final Warehouse warehouse;

  protected final Storage storage;
  protected final Location entryLocation;
  protected final Location exitLocation;
  protected final Robot robot;
  private final Location centerLocation;

  // ----------------------------------------------------------------------------

  public Solution(final Warehouse warehouse, final InputData input) {
    this.input = input;
    this.warehouse = warehouse;

    storage = warehouse.getStorage();
    entryLocation = storage.getEntryLocation();
    exitLocation = storage.getExitLocation();
    robot = storage.getRobot();

    // TODO: prepare data structures
    try {
      centerLocation = storage.getLocation(0, 13);
    } catch (NoSuchLocationException e) {
      throw new IllegalStateException(e);
    }

  }

  // ----------------------------------------------------------------------------

  /**
   * The main entry-point
   */
  public void run() throws Exception {
    // TODO: make calls to API (see below)
    while (warehouse.hasNextOrder()) {
      Order order = warehouse.nextOrder();
      List<Product> todo = order.getProducts().stream()
              .sorted(Comparator.comparingInt(Product::getWidth))
              .collect(Collectors.toList());

      System.out.println("Order: " + order.getCode());

      while (!todo.isEmpty()) {
        Iterator<Product> iterator = todo.iterator();
        while (iterator.hasNext()) {
          Product p = iterator.next();
          Location l = nearestLocationOf(p.getCode());

          if (l != null && robot.getRemainingLength() >= p.getLength()) {
            robot.pullFrom(l);
          }
        }

        List<Product> roboProducts = new ArrayList<>(robot.getCurrentProducts());
        for (Product p : roboProducts) {
          robot.pushTo(exitLocation);
          todo.remove(p);
        }

        if (!roboProducts.isEmpty()) {
          continue;
        }

        while (!entryLocation.getCurrentProducts().isEmpty()) {
          Product p = entryLocation.getCurrentProducts().get(0);

          if (robot.getRemainingLength() < p.getLength() || robot.getCurrentMaxWidth() > p.getWidth()) {
            break;
          }
          robot.pullFrom(entryLocation);
        }

        while (!robot.getCurrentProducts().isEmpty()) {
          pushToNearestStorage();
        }
      }
    }
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  /**
   * Just for documentation purposes.
   *
   * Method may be removed without any side-effects
   *
   *   divided into 4 sections
   *
   *     <li><em>input methods</em>
   *
   *     <li><em>main interaction methods</em>
   *         - these methods are the ones that make (explicit) changes to the warehouse
   *
   *     <li><em>information</em>
   *         - information you might need for your solution
   *
   *     <li><em>additional information</em>
   *         - various other infos: statistics, information about (current) costs, ...
   *
   */
  @SuppressWarnings("unused")
  private void apis() throws Exception {
    // ----- input -----

    final List<Product> allProductsAtEntry = input.getAllProductsAtEntry();
    final List<Order> allOrders = input.getAllOrders();

    final List<Product> remainingProducts = warehouse.getRemainingProductsAtEntry();
    final List<Order> remainingOrders = warehouse.getRemainingOrders();

    final Location location0 = storage.getLocation(0, 0);
    final List<Location> allLocations = storage.getAllLocations();

    // ----- main interaction methods -----

    Location location;

    location = entryLocation;
    robot.pullFrom(location);

    location = exitLocation;
    robot.pushTo(location);

    final Order order = warehouse.nextOrder();

    // ----- information -----

    final Product product = order.getProducts().get(0);

    location.getType();
    location.getLevel();
    location.getPosition();
    location.getLength();
    location.getRemainingLength();
    location.getCurrentProducts();

    final Location lamLocation = robot.getCurrentLocation();
    robot.getCurrentProducts();
    robot.getLength();
    robot.getRemainingLength();
    robot.getCurrentMaxWidth();

    // ----- additional information -----
    final WarehouseInfo info = warehouse.getInfoSnapshot();
  }

  // ----------------------------------------------------------------------------

  private Location nearestLocationOf(String productCode) {
    return storage.getAllLocations().stream()
            .filter(l -> !l.getCurrentProducts().isEmpty())
            .filter(l -> l.getCurrentProducts().get(0).getCode().equals(productCode))
            .filter(l -> l != exitLocation)
            .min(Comparator.comparingDouble(this::distanceRobot))
            .orElse(null);
  }

  private void pushToNearestStorage() throws Exception {
    Product product = robot.getCurrentProducts().get(0);

    Location location = storage.getAllLocations().stream()
            .filter(l -> l != entryLocation && l != exitLocation)
            .filter(l -> l.getCurrentProducts().isEmpty() ||
                    (l.getCurrentProducts().get(0).getCode().equals(product.getCode()) &&
                            l.getRemainingLength() >= product.getLength()))
            .min(Comparator.comparingDouble(this::distanceCenter))
            .orElseThrow(RuntimeException::new);

    robot.pushTo(location);
  }

  private double distance(Location l1, Location l2) {
    double levelDiff = l1.getLevel() - l2.getLevel();
    double positionDiff = l1.getPosition() - l2.getPosition();
    return Math.hypot(levelDiff, positionDiff);
  }

  private double distanceRobot(Location location) {
    return this.distance(robot.getCurrentLocation(), location);
  }

  private double distanceCenter(Location location) {
    return this.distance(centerLocation, location);
  }
}
