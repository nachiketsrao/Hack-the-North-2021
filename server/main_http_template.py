from http.server import HTTPServer, BaseHTTPRequestHandler

class helloHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        #200 is the status response code
        self.send_header('content-type','text/html')
        self.end_headers()
        self.wfile.write('hello'.encode())

PORT = 8080
server = HTTPServer(('',PORT), helloHandler)
print(f'Server running on port {PORT}')
server.serve_forever()