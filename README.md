# 🏗 Software Architecture 👷🏻‍♂️

Rasukan menggunakan desain arsitektur berupa **Microservices Architecture**. Microservice architecture adalah arsitektur yang memegang prinsip domain driven design, yakni membagi atau memisah fitur-fitur sesuai logika bisnis. Cara kami menerapkan arsitektur microservice yaitu dengan membagi fitur-fitur ke dalam repository GitHub. 
Alasan kami menggunakan Microservices Architecture adalah dapat meningkatkan kualitas proyek kode dalam hal:
1. **Skalabilitas🏋🏻‍♀️**
<br> Dengan membagi-bagi fitur atau layanan ke berbagai microservice yang dideploy secara terpisah, akan meningkatkan skalabilitas proyek karena akan dapat menangani lebih banyak request setiap service nya. 
2. **keuntungan2**
3. **keuntungan3**
4. **keuntungan4**

Pada microservice ini menangani service terkait staff, featured listing, dan payment. Kami mengelompokkan ketiga scope ini ke dalam satu microservice karena mereka saling berkaitan kuat satu sama lain. Misalnya staf harus bisa melihat dan menyetujui payment serta mengatur featured listing. Data atau service yang dibutuhkan namun berada di microservice lain akan diambil melalui penembakan endpoint menggunakan ```restTemplate```.