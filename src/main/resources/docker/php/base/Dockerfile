FROM php:5.6-apache
RUN  apt-get update
RUN  apt-get install -y \
        libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng-dev \
  && docker-php-ext-install -j$(nproc) iconv mcrypt \
  && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
  && docker-php-ext-install -j$(nproc) gd \
  && docker-php-ext-install mysql \
  && docker-php-ext-install mysqli \
  ## pdo pdo_mysql | 为了解决Undefined class constant 'MYSQL_ATTR_INIT_COMMAND'
  && docker-php-ext-install pdo \
  && docker-php-ext-install pdo_mysql