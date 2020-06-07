### Iteration 3 - Style-And-Git Partial Assessment (Graded By: Subhankar Ghosh)

Run on May 10, 11:41:47 AM.

<hr>

This Partial Assessment indicates a TA has begun grading your Iteration 3 Design. However, design will be graded using a Canvas rubric and grades will only be posted on Canvas once every student's program design has been graded. We ask for your patience at this time.

<hr>

+ Pass: Checkout iteration 3 final submission.




#### Style Compliance Tests

+ Pass: Change into directory "project/src".

+ Pass: Linting main src files...



+ Pass: Change into directory "../web_code/web".

+ Fail: Linting web code...

<pre>Processing visualization_simulator.cc
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.cc:154:  Using C-style cast.  Use static_cast<double>(...) instead  [readability/casting] [4]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.cc
Total errors found: 1
Processing visualization_simulator.h
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_VISUALIZATION_SIMULATOR_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:55:  #endif line should be "#endif  // WEB_CODE_WEB_VISUALIZATION_SIMULATOR_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:9:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:11:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h:55:  At least two spaces is best between code and comments  [whitespace/comments] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/visualization_simulator.h
Total errors found: 6
Processing main.cpp
/export/scratch/cpplint-ghosh117/web_code/web/main.cpp:43:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/main.cpp
Total errors found: 1
Processing my_web_server.cc
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:2:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:6:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:7:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:7:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:11:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:12:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:15:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:33:  Line ends in whitespace.  Consider deleting these extra spaces.  [whitespace/end_of_line] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:33:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:34:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:37:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:42:  Line ends in whitespace.  Consider deleting these extra spaces.  [whitespace/end_of_line] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc:47:  Line ends in whitespace.  Consider deleting these extra spaces.  [whitespace/end_of_line] [4]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server.cc
Total errors found: 14
Processing my_web_server.h
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_MY_WEB_SERVER_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:24:  #endif line should be "#endif  // WEB_CODE_WEB_MY_WEB_SERVER_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:7:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:11:  Line ends in whitespace.  Consider deleting these extra spaces.  [whitespace/end_of_line] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:11:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:12:  public: should be indented +1 space inside class MyWebServer  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:21:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:24:  At least two spaces is best between code and comments  [whitespace/comments] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h:24:  Should have a space between // and comment  [whitespace/comments] [4]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server.h
Total errors found: 10
Processing my_web_server_command.cc
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:2:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:7:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:14:  Should have a space between // and comment  [whitespace/comments] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:29:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:53:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:60:  Should have a space between // and comment  [whitespace/comments] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:70:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:71:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:91:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:92:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:92:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:94:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:101:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:103:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:109:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:112:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:123:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:134:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:140:  public: should be indented +1 space inside class BusWebObserver  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:141:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:143:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:143:  At least two spaces is best between code and comments  [whitespace/comments] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:149:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:156:  private: should be indented +1 space inside class BusWebObserver  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:160:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:162:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:170:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:172:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc:184:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.cc
Total errors found: 30
Processing my_web_server_command.h
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_MY_WEB_SERVER_COMMAND_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:88:  #endif line should be "#endif  // WEB_CODE_WEB_MY_WEB_SERVER_COMMAND_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:5:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:6:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:8:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:9:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:10:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:11:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:13:  Found C++ system header after other header. Should be: my_web_server_command.h, c system, c++ system, other.  [build/include_order] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:19:  public: should be indented +1 space inside class MyWebServerCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:20:  Tab found; better to use spaces  [whitespace/tab] [1]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:21:  Tab found; better to use spaces  [whitespace/tab] [1]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:21:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:21:  Is this a non-const reference? If so, make const or use a pointer: picojson::value& command  [runtime/references] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:28:  public: should be indented +1 space inside class GetRoutesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:29:  Tab found; better to use spaces  [whitespace/tab] [1]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:29:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:30:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:31:  private: should be indented +1 space inside class GetRoutesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:36:  public: should be indented +1 space inside class GetBussesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:37:  Tab found; better to use spaces  [whitespace/tab] [1]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:37:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:38:  Tab found; better to use spaces  [whitespace/tab] [1]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:38:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:39:  private: should be indented +1 space inside class GetBussesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:44:  public: should be indented +1 space inside class StartCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:45:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:46:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:47:  private: should be indented +1 space inside class StartCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:54:  public: should be indented +1 space inside class UpdateCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:55:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:56:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:57:  private: should be indented +1 space inside class UpdateCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:62:  public: should be indented +1 space inside class PauseCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:63:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:64:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:65:  private: should be indented +1 space inside class PauseCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:67:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:71:  public: should be indented +1 space inside class AddListenerCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:72:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:73:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:74:  private: should be indented +1 space inside class AddListenerCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:79:  public: should be indented +1 space inside class InitRoutesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:80:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:81:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:82:  private: should be indented +1 space inside class InitRoutesCommand  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h:88:  At least two spaces is best between code and comments  [whitespace/comments] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server_command.h
Total errors found: 48
Processing my_web_server_session.cc
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:2:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:3:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:8:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:8:  Should have a space between // and comment  [whitespace/comments] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc:9:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.cc
Total errors found: 6
Processing my_web_server_session.h
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_MY_WEB_SERVER_SESSION_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:22:  #endif line should be "#endif  // WEB_CODE_WEB_MY_WEB_SERVER_SESSION_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:6:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:10:  public: should be indented +1 space inside class MyWebServerSession  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:11:  Single-parameter constructors should be marked explicit.  [runtime/explicit] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:17:  private: should be indented +1 space inside class MyWebServerSession  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h:22:  At least two spaces is best between code and comments  [whitespace/comments] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session.h
Total errors found: 8
Processing my_web_server_session_state.h
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_MY_WEB_SERVER_SESSION_STATE_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h:15:  #endif line should be "#endif  // WEB_CODE_WEB_MY_WEB_SERVER_SESSION_STATE_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h:11:  Lines should be <= 80 characters long  [whitespace/line_length] [2]
/export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h:15:  At least two spaces is best between code and comments  [whitespace/comments] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/my_web_server_session_state.h
Total errors found: 5
Processing web_interface.h
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:0:  No copyright message found.  You should have a line: "Copyright [year] <Copyright Owner>"  [legal/copyright] [5]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:2:  #ifndef header guard has wrong style, please use: WEB_CODE_WEB_WEB_INTERFACE_H_  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:19:  #endif line should be "#endif  // WEB_CODE_WEB_WEB_INTERFACE_H_"  [build/header_guard] [5]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:5:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:6:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:7:  Include the directory when naming .h files  [build/include] [4]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:10:  Redundant blank line at the start of a code block should be deleted.  [whitespace/blank_line] [2]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:11:  public: should be indented +1 space inside class WebInterface  [whitespace/indent] [3]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:13:  Line ends in whitespace.  Consider deleting these extra spaces.  [whitespace/end_of_line] [4]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:16:  Redundant blank line at the end of a code block should be deleted.  [whitespace/blank_line] [3]
/export/scratch/cpplint-ghosh117/web_code/web/web_interface.h:19:  At least two spaces is best between code and comments  [whitespace/comments] [2]
Done processing /export/scratch/cpplint-ghosh117/web_code/web/web_interface.h
Total errors found: 11
</pre>




#### Git Tests

+  _0.67_ / _2_ : Pass: Counting Iteration 3 branches...

Insufficient branches found (found=2, required=6):

lab16

lab17

+  _2.0_ / _2_ : Pass: Run ghi for total number of issues in Github repo (Found: 6, Expected: 4) 

 [OPEN issue #41] :  lab17

[CLOSED issue #43] :  Lab17 ↑

[CLOSED issue #42] :  Devel ↑

[CLOSED issue #40] :  Lab16 ↑

[CLOSED issue #39] :  lab16

[CLOSED issue #25] :  skip stops [enhancement]

 




#### Counting commits on devel

+ Pass: Checkout devel branch.



+ Pass: Gather commit history




		[Sami Frank] 2020-04-22 (origin/lab16, lab16) lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-22 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 

		[Sami Frank] 2020-04-21 lab16 testing different proccessoutput input 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 



		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-20 lab16 

		[Sami Frank] 2020-04-17 pulls upstream code 















+  _1_ / _1_ : Pass: Check git commit history
Sufficient commits (found=178,required=10)

+ Pass: Opening Iter_03_Style-And-Git_Assessment.md using firefox

