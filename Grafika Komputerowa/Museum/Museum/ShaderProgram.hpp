/*
Niniejszy program jest wolnym oprogramowaniem; możesz go
rozprowadzać dalej i / lub modyfikować na warunkach Powszechnej
Licencji Publicznej GNU, wydanej przez Fundację Wolnego
Oprogramowania - według wersji 2 tej Licencji lub(według twojego
wyboru) którejś z późniejszych wersji.

Niniejszy program rozpowszechniany jest z nadzieją, iż będzie on
użyteczny - jednak BEZ JAKIEJKOLWIEK GWARANCJI, nawet domyślnej
gwarancji PRZYDATNOŚCI HANDLOWEJ albo PRZYDATNOŚCI DO OKREŚLONYCH
ZASTOSOWAŃ.W celu uzyskania bliższych informacji sięgnij do
Powszechnej Licencji Publicznej GNU.

Z pewnością wraz z niniejszym programem otrzymałeś też egzemplarz
Powszechnej Licencji Publicznej GNU(GNU General Public License);
jeśli nie - napisz do Free Software Foundation, Inc., 59 Temple
Place, Fifth Floor, Boston, MA  02110 - 1301  USA
*/


#ifndef SHADERPROGRAM_H
#define SHADERPROGRAM_H

#include <GL/glew.h>
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "glm/gtc/matrix_transform.hpp"
#include "stdio.h"

class ShaderProgram {
private:
	GLuint shaderProgram;
	GLuint vertexShader;
	GLuint geometryShader;
	GLuint fragmentShader;
	char* readFile(char* fileName);
	GLuint loadShader(GLenum shaderType,char* fileName);
public:
	ShaderProgram(char* vertexShaderFile,char* geometryShaderFile,char* fragmentShaderFile);
	~ShaderProgram();
	void use();
	GLuint getUniformLocation(char* variableName);
	GLuint getAttribLocation(char* variableName);
    void setUniform(char *variableName, GLsizei count, glm::mat4 &value);
    void setUniform(char *variableName, GLsizei count, glm::vec3 &value);
    void setUniform(char *variableName, GLsizei count, glm::vec4 &value);
    void setUniformInt(char *variableName, int value);
    void setUniform(char *variableName, float value);
};



#endif