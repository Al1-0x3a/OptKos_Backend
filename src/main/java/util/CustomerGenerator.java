/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package util;

import data_loader.data_access_object.CustomerCategoryDao;
import data_loader.data_access_object.CustomerDao;
import data_models.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CustomerGenerator {
    private static String[] FIRSTNAMESMALE = {"Ben", "Jonas", "Leon", "Elias", "Finn", "Noah", "Paul", "Luis", "Lukas", "Luca", "Felix", "Maximilian", "Henry", "Max", "Emil",
            "Moritz",
            "Jakob", "Niklas", "Tim", "Julian", "Oskar", "Anton", "Philipp", "David", "Liam", "Alexander", "Theo", "Tom", "Mats", "Jan", "Matteo", "Samuel", "Erik", "Fabian",
            "Milan", "Leo", "Jonathan", "Rafael", "Simon", "Vincent", "Lennard", "Carl", "Linus", "Hannes", "Jona", "Mika", "Jannik", "Nico", "Till", "Johannes", "Marlon",
            "Leonard", "Benjamin", "Johann", "Mattis", "Adrian", "Julius", "Florian", "Constantin", "Daniel", "Aaron", "Maxim", "Nick", "Lenny", "Valentin", "Ole", "Luke", "Levi",
            "Nils", "Jannis", "Sebastian", "Tobias", "Marvin", "Joshua", "Mohammed", "Timo", "Phil", "Joel", "Benedikt", "John", "Robin", "Toni", "Dominic", "Damian", "Artur",
            "Pepe", "Lasse", "Malte", "Sam", "Bruno", "Gabriel", "Lennox", "Justus", "Kilian", "Theodor", "Oliver", "Jamie", "Levin", "Lian", "Noel"};

    private static String[] FIRSTNAMESFEMALE = {"Mia", "Emma", "Hannah", "Sofia", "Anna", "Emilia", "Lina", "Marie", "Lena", "Mila", "Emily", "Lea", "Léonie", "Amelie", "Sophie",
            "Johanna", "Luisa", "Clara", "Lilly", "Laura", "Nele", "Lara", "Charlotte", "Leni", "Maja", "Frieda", "Mathilda", "Ida", "Ella", "Pia", "Sarah", "Lia", "Lotta",
            "Greta", "Melina", "Julia", "Paula", "Lisa", "Marlene", "Zoe",
            "Alina", "Victoria", "Mira", "Elisa", "Isabella", "Helena", "Josephine", "Mara", "Isabell", "Nora", "Antonia", "Lucy", "Emely", "Jana", "Pauline", "Amy", "Anni",
            "Merle", "Finja", "Katharina", "Luise", "Elena", "Theresa", "Annika", "Luna", "Romy", "Maria", "Stella", "Fiona", "Jasmin", "Magdalena", "Jule", "Milena", "Mina",
            "Carla", "Eva", "Martha", "Nina", "Annabell", "Melissa", "Elina", "Carlotta", "Paulina", "Maila", "Elif", "Elisabeth", "Ronja", "Zoey", "Chiara", "Tilda", "Miriam",
            "Franziska", "Valentina", "Juna", "Linda", "Thea", "Elli", "Rosalie", "Selina", "Fabienne"};

    private static final String[] LASTNAMES = {"Howell", "Lutz", "Quinn", "Calderon", "Carter", "James", "Price", "Terry", "Flowers", "Meadows", "Mcfarland", "Gallegos", "Marquez",
            "Hartman", "Baker", "Conley", "Galloway", "Whitaker", "Peck", "Ramirez", "Fisher", "Kramer", "Luna", "Singleton", "Villegas", "Wu", "Coffey", "Davis", "Ross", "Oliver",
            "Harding", "Mcguire", "Arellano", "Bernard", "Gallagher", "Griffith", "Middleton", "Vincent", "Hinton", "Byrd", "Soto", "Ball", "Mccoy", "Brooks", "Hale", "Sherman",
            "Werner", "Pena", "Carroll", "Jackson", "Bates", "Carey", "Cummings", "Maddox", "Cox", "Mendoza", "Mathews", "Winters", "Owen", "Hogan", "Key", "Friedman", "Kelly",
            "Valdez", "Ballard", "Guerrero", "Eaton", "Peters", "Lang", "Tanner", "Lane", "Ramos", "Hudson", "Nicholson", "Beltran", "Lin", "Cunningham", "Gomez", "Boyer",
            "Blackwell", "Stone", "Webb", "Calhoun", "Stein", "Brock", "Hebert", "Frederick", "Foley", "Campos", "Francis", "Blankenship", "Fowler", "Gonzales", "Erickson",
            "Esparza", "Hicks", "Oconnor", "Rollins", "Juarez", "Woods", "Merritt", "White", "Reilly", "Burgess", "Tate", "Nixon", "Gentry", "Gonzalez", "Klein", "Moyer",
            "Vazquez", "Chen", "Roman", "Richardson", "Horton", "Archer", "Robinson", "Savage", "Harris", "Costa", "Case", "Stuart", "Mosley", "Oconnell", "Carney", "Fitzgerald",
            "Reese", "Sellers", "Harrington", "Dominguez", "Meyer", "Gilmore", "Keller", "Frey", "Mason", "Williamson", "Willis", "Gutierrez", "Suarez", "Rowe", "Chavez",
            "Elliott", "Stephens", "Bauer", "Williams", "Ryan", "Powers", "Wheeler", "Christensen", "Mercer", "Everett", "Morgan", "Rush", "Cherry", "Ashley", "Parker", "Thompson",
            "Hobbs", "Murray", "Medina", "Krause", "Solomon", "Cordova", "Knapp", "Stevenson", "Newman", "Bush", "Beasley", "Barton", "Woodward", "Manning", "Arnold", "Long",
            "Velasquez", "Gates", "Atkins", "Arias", "Johnston", "Santos", "Krueger", "Humphrey", "Hoffman", "Bruce", "Valenzuela", "Tran", "Swanson", "Matthews", "Shelton",
            "Khan", "Glass", "Sosa", "Craig", "Herrera", "Boyle", "Ferrell", "Cruz", "Andrade", "Hendricks", "Kerr", "French"};

    private static final String[] STREET = {"Colonial Avenue", "College Street", "Essex Court", "Lincoln Avenue", "Ridge Road", "6th Street", "Circle Drive", "Creekside Drive",
            "Route 44", "Country Club Road", "River Road", "Cleveland Avenue", "School Street", "Sunset Drive", "14th Street", "Evergreen Lane", "Linden Street", "Glenwood Avenue",
            "Harrison Street", "Monroe Drive", "Devon Road", "Route 30", "Center Street", "Myrtle Street", "Sycamore Drive", "Liberty Street", "Myrtle Avenue", "Queen Street",
            "Green Street", "Elm Avenue", "Shady Lane", "Mill Road", "Heather Lane", "Windsor Drive", "Heritage Drive", "Wall Street", "Durham Road", "Fawn Court", "Pin Oak Drive",
            "Hilltop Road", "Summit Avenue", "Pennsylvania Avenue", "Cherry Street", "Route 29", "Grant Avenue", "8th Street West", "Bridge Street", "5th Street West",
            "Oak Street", "Hill Street", "Hillside Avenue", "Linda Lane", "Schoolhouse Lane", "Pearl Street", "Railroad Avenue", "Ridge Avenue", "Main Street North", "Arch Street",
            "Park Drive", "Cottage Street", "Main Street East", "Market Street", "Church Street South", "Park Street", "Washington Street", "4th Street", "Spring Street",
            "Cambridge Road", "Hartford Road", "Eagle Road", "Hudson Street", "8th Street", "Aspen Court", "Linden Avenue", "Marshall Street", "Summer Street", "Hillside Drive",
            "Canterbury Road", "Inverness Drive", "Victoria Court", "Cypress Court", "Meadow Lane", "White Street", "Madison Street", "Franklin Avenue", "North Avenue",
            "Laurel Drive", "Walnut Street", "Cobblestone Court", "Cedar Lane", "Elmwood Avenue", "Dogwood Drive", "Overlook Circle", "Willow Drive", "Forest Drive",
            "2nd Street North", "5th Street South", "Locust Street", "Belmont Avenue", "York Road", "Sycamore Lane", "Briarwood Court", "Forest Street", "Dogwood Lane",
            "6th Street West", "Canterbury Drive", "3rd Street West", "Cardinal Drive", "Jefferson Street", "Route 41", "1st Avenue", "Colonial Drive", "Morris Street",
            "Andover Court", "Pheasant Run", "Warren Street", "Sheffield Drive", "Church Street North", "Main Street", "Garfield Avenue", "East Street", "Roosevelt Avenue",
            "Depot Street", "Winding Way", "Sunset Avenue", "Eagle Street", "Willow Street", "Valley Drive", "Durham Court", "Jefferson Avenue", "Virginia Street", "Spruce Street",
            "Old York Road", "Meadow Street", "Country Club Drive", "Cedar Street", "Pleasant Street", "Maple Street", "Penn Street", "7th Street", "Devonshire Drive",
            "8th Street South", "Prospect Street", "Bridle Lane", "Maple Avenue", "Franklin Court", "Woodland Drive", "Fairway Drive", "Grand Avenue", "Division Street",
            "Spruce Avenue", "South Street", "Route 5", "Clark Street", "Lafayette Street", "Fairview Road", "Bay Street", "5th Street North", "Woodland Road", "4th Street South",
            "Prospect Avenue", "4th Avenue", "Hickory Lane", "Devon Court", "Country Lane", "Mulberry Lane", "Route 2", "Edgewood Drive", "Smith Street", "Hillcrest Drive",
            "Academy Street", "Monroe Street", "Crescent Street", "Charles Street", "Route 70", "Clay Street", "Route 64", "5th Street East", "7th Avenue", "Rosewood Drive",
            "Sherman Street", "Magnolia Drive", "Route 9", "Columbia Street", "Hamilton Street", "Hillcrest Avenue", "Chestnut Street", "Ann Street", "Williams Street",
            "12th Street", "Fulton Street", "Grove Street", "Orchard Avenue", "Poplar Street", "Augusta Drive", "Orchard Lane", "Oak Lane", "Glenwood Drive", "Howard Street",
            "6th Avenue"};

    private static final String[] CITY = {"Berlin ", "Hamburg", "München", "Köln", "Frankfurt am Main", "Stuttgart", "Düsseldorf", "Dortmund", "Essen", "Leipzig", "Bremen",
            "Dresden", "Hannover", "Nürnberg", "Duisburg", "Bochum", "Wuppertal", "Bielefeld", "Bonn", "Münster", "Karlsruhe", "Mannheim", "Augsburg", "Wiesbaden", "Gelsenkirchen",
            "Mönchengladbach", "Braunschweig", "Chemnitz", "Kiel", "Aachen", "Halle (Saale)", "Magdeburg", "Freiburg im Breisgau", "Krefeld", "Lübeck", "Oberhausen", "Erfurt",
            "Mainz", "Rostock", "Kassel", "Hagen", "Hamm", "Saarbrücken", "Mülheim (Ruhr)", "Potsdam", "Ludwigshafen", "Leverkusen", "Oldenburg", "Osnabrück", "Solingen",
            "Heidelberg", "Herne", "Neuss", "Darmstadt", "Paderborn", "Regensburg"};

    private static final String[] MAILPROVIDER = {"aol.com", "att.net", "comcast.net", "facebook.com", "gmail.com", "gmx.com", "googlemail.com",
            "google.com", "hotmail.com", "hotmail.co.uk", "mac.com", "me.com", "mail.com", "msn.com",
            "live.com", "sbcglobal.net", "verizon.net", "yahoo.com", "yahoo.co.uk",};
    private static final String[] PHONEDESC = {"Geschäftlich" , "Privat" , "Mobil" , "Hauptnummer"};

    public static void main(String[] args) {
        Random random = new Random();
        List<CustomerCategory> customerCategories = CustomerCategoryDao.getAllCustomerCategoriesFromDb();

        for (int i = 0; i <= 1000; i++) {
            String personId = UUID.randomUUID().toString();
            Customer customer = new Customer(personId);

            // male or female
            if (random.nextBoolean()) {
                String firstName = FIRSTNAMESMALE[random.nextInt(FIRSTNAMESMALE.length)];
                customer.setFirstname(firstName);
                customer.setGender(Person.GENDER.M);
                customer.setSalutation(Person.SALUTATION.Herr);
            } else {
                String firstName = FIRSTNAMESFEMALE[random.nextInt(FIRSTNAMESFEMALE.length)];
                customer.setFirstname(firstName);
                customer.setGender(Person.GENDER.W);
                customer.setSalutation(Person.SALUTATION.Frau);
            }

            String lastName = LASTNAMES[random.nextInt(LASTNAMES.length)];
            customer.setLastname(lastName);

            customer.setTitle(Person.TITLE.Default);
            customer.setAnnotation("");

            customer.setCustomerCategory(customerCategories.get(random.nextInt(customerCategories.size())));

            Address address = new Address(personId);
            String street = STREET[random.nextInt(STREET.length)];
            address.setStreet(street);
            String city = CITY[random.nextInt(CITY.length)];
            address.setCity(city);
            String plz = String.valueOf(random.nextInt(90000) + 9999);
            address.setPostcode(plz);
            String housnr = String.valueOf(random.nextInt(100));
            address.setHousenr(housnr);
            address.setAddition("");
            customer.setAddress(address);

            Email email = new Email(personId);
            String mail = customer.getFirstname() + customer.getLastname() + "@" + MAILPROVIDER[random.nextInt(MAILPROVIDER.length)];
            email.setEmailAddress(mail);
            customer.getEmailList().add(email);

            Phone phone = new Phone(personId);
            String phoneNr = String.valueOf(random.nextInt(999999999));
            phone.setNumber(phoneNr);
            phone.setAnnotation("");
            phone.setDescription(PHONEDESC[random.nextInt(PHONEDESC.length)]);
            customer.getPhoneList().add(phone);

            CustomerDao.createNewCustomer(customer);
            System.out.println("Kunde angelegt" + customer.toString());
        }
    }
}
