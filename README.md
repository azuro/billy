#Billy
=====

 Billy is currently a project in it's nascent stage, and I expect to improve it to a point that it is a _real_ solution to the problem at hand.

Things to be implemented:
--------------------------------------

###Back End:

####Database

 - SQLite Database **_Done_**
 - ~~JDBI Interfaces to make database queries more efficient~~ Used own implementation.

####Handlers
 - Add:
  - Customer
  - Collector
  - Area
  - Manager
  - Investor
 - Modify:
  - Customer
  - Collector
 - Payment:
  - Customer
 - Performance
  - Turnover
  - Expenses
 - Salary
  - Collector
  - Investor
  - Manager


####Housekeeping
 - Add monthly rate to current customer dues.
 - Clean out Frozen/Deleted/Overdue accounts on prompts by the admin.
 - Session Cookies

###Front End:
 - Login Page
 - Account Panel
 - Handler Pages

##Thanks to:
I used the following projects, and am immensely thankful to the authors of these libraries:

 - [SQLite-JDBC](https://bitbucket.org/xerial/sqlite-jdbc)
 - [Jetty](http://www.eclipse.org/jetty/)
 - [Web Look and Feel](http://weblookandfeel.com/)
 - [Divshot](http://www.divshot.com/)

##Licensing

This project is licensed under the [GNU GPL v3](http://www.gnu.org/copyleft/gpl.html) so feel free to hack away at it:

    Billy - The friendly billing daemon.
    Copyright (C) 2013  Anish Basu.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
