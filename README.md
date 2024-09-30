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

### Có write through cache

TODO

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

Để bắt đầu chạy benchmark thì chỉ cần chạy lệnh sau (10 virtual user, 20s)
```sh
k6 run benchmark-script.js
```

## Các Endpoint

Demo gồm các endpoint:
- `/`: trả về hello world
- `/secret`: trả về dữ liệu bí mật, chỉ truy cập được sau khi đăng nhập
- `/auth/register`: Đăng ký, nhận vào thông tin người dùng trong body với dạng json gồm `username` và `password`.
Ví dụ body: `{"username": "hello", "password": "world"}`.
- `/auth/login`: Đăng nhập, nhận vào thông tin người dùng trong body với dạng json gồm `username` và `password`.
Cài đặt `SESSION_TOKEN` để lưu trữ thông tin session nếu đăng nhập thành công.
Ví dụ body: `{"username": "hello", "password": "world"}`.
- `/auth/logout`: Gỡ `SESSION_TOKEN` và xóa dữ liệu trong CSDL.
