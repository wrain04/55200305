FROM nginx 

COPY ./dist /data 

RUN rm /etc/nginx/conf.d/default.conf

ADD product-ui.conf /etc/nginx/conf.d/ 

RUN /bin/bash -c 'echo init ok'