FROM nginx:latest
RUN mkdir -p /usr/share/nginx/html/www/pdf
COPY file-server/30MB-TESTFILE.pdf /usr/share/nginx/html/www/pdf/30MB-TESTFILE.pdf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]