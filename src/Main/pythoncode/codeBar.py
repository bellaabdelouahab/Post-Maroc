
# import barcode
# from barcode.writer import ImageWriter

# EAN = barcode.get_barcode_class('ean13')
# ean = EAN(u'000745862139', writer=ImageWriter())
# fullname = ean.save('my_ean13_barcode')


# from selenium import webdriver
# from selenium.webdriver.chrome.options import Options

# chrome_options = Options() #
# chrome_options.add_argument('--headless')

# chrome_options.add_argument("--window-size=1920x1080")
# driver = webdriver.Chrome(options=chrome_options, executable_path="C:/Users/Abdelouahab/Downloads/chromedriver_win32/chromedriver.exe")
# # service object
# driver.get("https://www.javafixing.com/2021/10/fixed-java-fx-preventing-ui-from.html")
# driver.get_screenshot_as_file("capture.png")





import pathlib


class LoC(object):
    suffixes = ['.py','.java','.fxml']

    def count(self, path: pathlib.Path, init=True):
        if init:
            self.root = path
            self.files = 0
            self.lines = 0
        if path.is_dir():
            # recursive case
            for item in path.iterdir():
                self.count(path=item, init=False)
        elif path.is_file() and path.suffix in self.suffixes:
            # base case
            with path.open(mode='r') as f:
                line_count = len(f.readlines())
            print(f'{path.relative_to(self.root)}: {line_count}')
            self.files += 1
            self.lines += line_count
        if init:
            print(f'\n{self.lines} lines in {self.files} files')


loc = LoC()
loc.count(path=pathlib.Path('./'))