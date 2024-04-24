# 🏗 Software Architecture 👷🏻‍♂️

Rasukan menggunakan desain arsitektur berupa **Microservices Architecture**. Microservice architecture adalah arsitektur yang memegang prinsip domain driven design, yakni membagi atau memisah fitur-fitur sesuai logika bisnis. Cara kami menerapkan arsitektur microservice yaitu dengan membagi fitur-fitur ke dalam repository GitHub.
Alasan kami menggunakan Microservices Architecture adalah dapat meningkatkan kualitas proyek kode dalam hal:
1. **Skalabilitas🏋🏻‍♀️**
   <br> Dengan membagi-bagi fitur atau layanan ke berbagai microservice yang dideploy secara terpisah, akan meningkatkan skalabilitas proyek karena akan dapat menangani lebih banyak request setiap service nya.
2. **Fleksibilitas🌟**
   <br> Dengan menggunakan microservices, setiap service dapat dikembangkan dan dikelola secara independen. Jadi, setiap anggota kelompok kami dapat mengimplementasikan fitur dalam layanan masing-masing tanpa terikat pada fitur lainnya.
3. **Pemeliharaan yang Mudah🛠️**
    <br> Pemeliharaan kode dan debugging menjadi lebih mudah dalam arsitektur microservices karena setiap layanan beroperasi secara independen. Perubahan atau pembaruan dalam satu layanan tidak mempengaruhi layanan lainnya, sehingga risiko perubahan yang merusak menjadi lebih rendah.
4. **Resilience (Ketahanan)💪🏼**
   <br> Microservices memungkinkan aplikasi untuk tetap berjalan ketika ada kegagalan di satu atau beberapa bagian. Jadi, ketika salah satu layanan mengalami masalah, layanan lain masih dapat beroperasi tanpa gangguan, asalkan interaksi antar layanan diatur dengan baik.

Pada microservice ini menangani service terkait staff, featured listing, dan payment. Kami mengelompokkan ketiga scope ini ke dalam satu microservice karena mereka saling berkaitan kuat satu sama lain. Misalnya staf harus bisa melihat dan menyetujui payment serta mengatur featured listing. Data atau service yang dibutuhkan namun berada di microservice lain akan diambil melalui penembakan endpoint menggunakan ```restTemplate```.