/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjoySrc.servlets.user;

import penjoySrc.servlets.*;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import penjoy.ejb.car.Car;

import penjoy.ejb.car.CarSearchBean;
import penjoy.utils.LocationHelper;

/**
 *
 * @author Domenico
 */
@WebServlet(name = "MainPageServlet", urlPatterns = {"/MainPageServlet"})
public class MainPageServlet extends BaseServlet {

    @EJB
    private CarSearchBean m_carSearchBean;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processCarSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Test Cars inDB
        List<Car> availableCarList = m_carSearchBean.getAvailableCars();
        request.setAttribute("carsNum", availableCarList.size());
        String userLocation = "45.462,9.177";
        
        ServletOutputStream out = response.getOutputStream();
        out.println(getMainPageBody(availableCarList, userLocation));
        //getServletContext().getRequestDispatcher("/UserPages/mainPage.jsp").forward(request, response);
    }
    
// <editor-fold defaultstate="collapsed" desc="Test Function: getMainPageBody">
   private String getMainPageBody(List<Car> availableCarList, String userLocation) {
        String response = "";
        float userLatitude = LocationHelper.getLatitude(userLocation);
        float userLongitude = LocationHelper.getLongitude(userLocation);
        //Response Head
        response = response + "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    +"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    +"<title>Main Page</title>"
                    +"<center id=\"logo\"><a href=\"/PowerEnjoy-war\"><img src=\"http://localhost:8080/PowerEnjoy-war/resources/powerEnjoyLogo.png\" alt=\"logo\" style=\"width:350px;height:120px;\"/></a></center>\n" 
                    +"<link rel=\"stylesheet\" href=\"http://localhost:8080/PowerEnjoy-war/styles/main.css\" media=\"screen\"></head>";                        
        //Response Body
        response = response +"<body bgcolor=\"#006FF1\">";
        /*            +"<table border=\"0\"><col width=\"300px\" /><col width=\"800px\" />"
                    //+"<tr><td><comment>Car List will be here."
                    //+"<br>There are "+ availableCarList.size()+ " Cars Available</comment><br>"
                    //+"<br><comment>User Location: "+ userLocation +"</comment><br></td><\tr>\n"
                    +"<tr><td><nav><ul> <carbox>Car 1</carbox>\n" +
                "      <carbox>Car 2</carbox>\n" +
                "      <br>Car 3\n" +
                "      <br>Car 4\n" +
                "      <br>Car 5\n" +
                "      <br>Car 6 \n" +
                "      <br>Car 7 \n" +
                "      <br>Car 8\n" +
                "      <br>Car 9\n" +
                "      <br>Car 10\n" +
                "      <br>Car 11\n" +
                "      <br>Car 12\n" +
                "      <br>Car 13\n" +
                "      <br>Car 14\n" +
                "      <br>Car 15\n" +
                "      <br>Car 16\n" +
                "      <br>Car 17\n" +
                "      <br>Car 18\n" +
                "      <br>Car 19\n" +
                "      <br>Car 20\n" +
                "      <br>Car 21\n" +
                "      <br>Car 22\n" +
                "      <br>Car 23\n" +
                "      <br>Car 24\n" +
                "      <br>Car 25\n" +
                "      <br>Car 26\n" +
                "      <br>Car 27\n" +
                "      <br>Car 28\n" +
                "      <br>Car 29\n" +
                "      </ul></nav></td>";
        */         

        //Map Functions
        response = response +/*"<td>"+*/"<div id=\"map\"></div><script>\n"+
        //Car Click Path Function
                "function createWalkingPath(id, from, to, infoWindow, directionsService, directionsDisplay) {\n" +
"                directionsService.route({\n" +
"                    origin: from,\n" +
"                    destination: to,\n" +
"                    travelMode: 'WALKING'\n" +
"                }, function (response, status) {\n" +
"                    if (status === 'OK') {\n" +
"                        var legs = response.routes[0].legs\n"+
"                        var duration = legs[0].duration.text\n"+
"                        infoWindow.setPosition(to);\n" +
"                        infoWindow.setContent('CarID: '+id+' - Distance: '+duration);\n"+
"                        directionsDisplay.setDirections(response);\n" +                 
"                    } else {\n" +
"                        window.alert('Directions request failed due to ' + status);\n" +
"                    }\n" +
"                });\n" +
"            }\n"+
        //Map Function
                     "function initMap() {\n" +
"                    if (navigator.geolocation) {\n" +
"                        navigator.geolocation.getCurrentPosition(function (position) {\n" +
"                            var userPos1 = {\n" +
"                                lat: position.coords.latitude,\n" +
"                                lng: position.coords.longitude\n" +
"                            };\n" +
"                            userPos = userPos1;"+
"                            var userMarker = new google.maps.Marker({\n" +
"                                position: userPos,\n" +
"                                map: map\n" +
"                            });\n" +
"                            map.setCenter(userPos);\n" +
"                        }, function () {\n" +
"                            handleLocationError(true, infoWindow, map.getCenter());\n" +
"                        });\n" +
"                        var directionsService = new google.maps.DirectionsService;\n" +
"                        var directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers: true});\n"+
"                        var map = new google.maps.Map(document.getElementById('map'), {\n" +
"                            zoom: 13,\n" +
"                            center: userPos\n" +
"                        });\n"+
"                        directionsDisplay.setMap(map);\n"+
"                        var infoWindow = new google.maps.InfoWindow({map: map});\n"+
"                        var userPos = {lat: "+userLatitude+", lng: "+userLongitude+"};\n"+
"                        var carImage = \"http://localhost:8080/PowerEnjoy-war/resources/map_car_icon.png\";\n";
//"                        var markers = [];\n";
        int carCount = 0;
        for(Car aCar : availableCarList)
        {
            float lat = aCar.getLatitude();
            float lng = aCar.getLongitude();
            Long carid = aCar.getId();
            //Add Car Marker
            response = response + "var car"+carCount+" = {lat: "+lat+", lng: "+lng+"};\n"+
"                       var marker"+carCount+" = new google.maps.Marker({\n" +
"                            position: car"+carCount+",\n" +
"                            map: map,\n" +
"                            icon: carImage\n" +
"                        });\n";
//+"                        markers.push(marker"+carCount+");\n";
            //Add click Listener
            response = response + 
"                    marker"+carCount+".addListener('click', function () {\n" +
"                        createWalkingPath("+carid+", userPos, car"+carCount+", infoWindow, directionsService, directionsDisplay);\n" +
"                    });\n";
            carCount++;
        }
        //Error Handling
        response = response + "} else {\n" +
"                        // Browser doesn't support Geolocation\n" +
"                        handleLocationError(false, infoWindow, map.getCenter());\n" +
"                    }\n" +
"                }\n" +
"\n" +
"                function handleLocationError(browserHasGeolocation, infoWindow, pos) {\n" +
"                    infoWindow.setPosition(pos);\n" +
"                    infoWindow.setContent(browserHasGeolocation ?\n" +
"                            'Error: The Geolocation service failed.' :\n" +
"                            'Error: Your browser doesn\\'t support geolocation.');\n" +
"                }\n" +
"            </script>\n";
        //Google Maps Connection
        response = response + "<script async defer\n"
                    +"src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCmFrKyeDan2sXnWj7Uh9NYEwNvYsxtRFg&callback=initMap\">\n"
                    +"</script>"
                    +"<center><form action=\"/PowerEnjoy-war/Reservation\" method=\"post\">"
                    +"<input type=\"number\" placeholder=\"carID\" name=\"carID\" size=\"20\"><br>"
                    +"<input type=\"submit\" value=\"Reservation\"></form><br>"
                    +"<center><a href=\"http://localhost:8080/PowerEnjoy-war/Logout\">\n"
                    +"<input type=\"button\" value=\"Logout\">\n"
                    +"</a></body></html>";
        return response;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (sessionCheck(request, response)) {
            processCarSearch(request, response);
        } else {
            getServletContext().getRequestDispatcher("/Login/userLoginPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (sessionCheck(request, response)) {
            processCarSearch(request, response);
        } else {
            getServletContext().getRequestDispatcher("/Login/userLoginPage.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main Page Servlet";
    }// </editor-fold>

}
