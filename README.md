## Eureka

Eureka is a collaborative project done for Bogazici University SWE 574 course. 

[Our Wiki page](https://github.com/SWE574-Nerds/friendly-eureka/wiki) has a lot more in detail

Please see our Wiki and for details and Issues for ongoing discussions.


## Contribution

We gladly accept/welcome contributions in any form,

Please feel free to 
1. Create [Issues](https://github.com/SWE574-Nerds/friendly-eureka/issues)
2. Create a [Pull-Request](https://github.com/SWE574-Nerds/friendly-eureka/pulls)

Our contribution guidelines can be found [here](https://github.com/SWE574-Nerds/friendly-eureka/wiki/Contribution-Guidelines)


## How to run backend without docker

    $ git clone https://github.com/SWE574-Nerds/friendly-eureka.git
    $ cd friendly-eureka/backend/eureka
    $ pip3 install -r requirements.txt
    $ python3 manage.py runserver

Now you can go to 127.0.0.1:8000


## How to run with docker

    $ git clone https://github.com/SWE574-Nerds/friendly-eureka.git
    $ cd friendly-eureka
    $ docker-compose up -d 

Now you can go to 127.0.0.1:8000


Running Automated Tests

#### How to run automated tests:

    $ cd friendly-eureka/tests/friendlyeureka
    $ mvn test 
    
## How to run Eureka on an Android Device

Please download the file named [EurekaSWEAPK.apk](https://github.com/SWE574-Nerds/friendly-eureka/blob/master/EurekaSWEAPK.apk) on your Android device by clicking on the "Open" button on the link above from a Browser App on an Android Device.



