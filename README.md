Demo gồm các endpoint:
- `/`: trả về hello world
- `/secret`: trả về dữ liệu bí mật, chỉ truy cập được sau khi đăng nhập
- `/auth/register`: Đăng ký, nhận vào thông tin người dùng trong body với dạng json gồm `username` và `password`.
Ví dụ body: `{"username": "hello", "password: "world"}`.
- `/auth/login`: Đăng nhập, nhận vào thông tin người dùng trong body với dạng json gồm `username` và `password`.
Cài đặt `SESSION_TOKEN` để lưu trữ thông tin session nếu đăng nhập thành công.
Ví dụ body: `{"username": "hello", "password: "world"}`.
- `/auth/logout`: Gỡ `SESSION_TOKEN` và xóa dữ liệu trong CSDL.
