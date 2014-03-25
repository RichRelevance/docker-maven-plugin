File dockerFile = new File(basedir, "target/Dockerfile")

assert dockerFile.isFile()

FileReader r = new FileReader(dockerFile)
BufferedReader br = new BufferedReader(r)
ArrayList<String> lines = new ArrayList<String>()
String line = br.readLine()
while (line != null) {
    lines.add(line)
    line = br.readLine()
}

assert lines.contains("FROM centos:latest")
assert lines.contains("MAINTAINER ops@company.com")
assert lines.contains("CMD cd /srv/app && su user -c ./startup.sh")

int number_of_add_lines = 0
int number_of_expose_lines = 0


for (String evalLine : lines) {
    if (evalLine.startsWith("ADD")) {
        number_of_add_lines++
    } else if (evalLine.startsWith("EXPOSE")) {
        number_of_expose_lines++
    }
}

assert number_of_add_lines == 3
assert number_of_expose_lines == 2