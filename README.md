# Demo cache

## Chạy

### Không có cache

Chạy lệnh sau để bắt đầu web server (sử dụng postgres) ở `localhost:8080`
```sh
docker compose -f compose.database.yaml up
```

### Có cache

Chạy lệnh sau để bắt đầu web server (sử dụng postgres + redis) ở `localhost:8080`
```sh
docker compose -f compose.cache.yaml up
```

### Danh sách các port được mở

- postgresql
  - Port: 5432
  - Username: user
  - Password: user
  - Database: postgres
- redis
  - Port: 6379
  - Không username, password
- spring boot:
  - Port: 8080
  - Có thể xem openapi schema ở http://localhost:8080/schema/swagger

## Benchmark

### Dependency

Để benchmark được trước hết phải có [k6](https://k6.io/) cài đặt trước.

### Nội dung benchmark

1. Tạo username, pasword ngẫu nhiên
2. Đăng ký với username, password đó
3. Đăng nhập với username, password đó
4. Truy cập `/auth/secret` để đọc thông tin bí mật (ngẫu nhiên từ 1-30 lần)
5. Đăng xuất

### Cách chạy benchmark

Để bắt đầu chạy benchmark thì chỉ cần chạy lệnh sau:
```sh
k6 run benchmark-script.js
```

## Các Endpoint

Có thể xem chi tiết các endpoint ở http://localhost:8080/schema/swagger
