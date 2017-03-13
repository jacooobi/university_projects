//
//  ModelLoader.cpp
//  Museum
//
//  Created by Tomasz Kasperek on 16.07.2016.
//  Copyright © 2016 TKJK. All rights reserved.
//

#include "ModelLoader.hpp"

ModelLoader::ModelLoader() {}

ModelLoader::~ModelLoader() {
    if (texLoader != nullptr) {
        delete texLoader;
    }
}

Model* ModelLoader::load(std::string filename,
                         std::string vertexShaderPath,
                         std::string fragmentShaderPath) {
    std::vector<tinyobj::shape_t> shapes;
    std::vector<tinyobj::material_t> materials;
    
    std::string err;
    if (!tinyobj::LoadObj(shapes, materials, err, filename.c_str(), "materials/")) {
        std::cerr << "Error loading model [" << filename << "] " << err << std::endl;
        exit(1);
    }
    
    texLoader = new TextureLoader(materials);

    ShaderProgram *sp = new ShaderProgram((char*)vertexShaderPath.c_str(),
                                          nullptr,
                                          (char*)fragmentShaderPath.c_str());
    
    Model *model = new Model(sp);
    
    for (size_t i = 0; i < shapes.size(); ++i) {
        // TODO: either export model differently or add some case that will fix that
        // For now if shape has no texcoords it won't be rendered
        if (shapes[i].mesh.texcoords.size() > 0) {
            // Each vertex has same material, so for our program this should work :)
            int materialId = shapes[i].mesh.material_ids[0];
            model->addMesh(createMesh(shapes[i].mesh, materials[materialId], texLoader->get(materialId)));
        }
    }
    
    return model;
}

Doorman* ModelLoader::loadDoorman(std::string filename,
                         std::string vertexShaderPath,
                         std::string fragmentShaderPath) {
    std::vector<tinyobj::shape_t> shapes;
    std::vector<tinyobj::material_t> materials;
    
    std::string err;
    if (!tinyobj::LoadObj(shapes, materials, err, filename.c_str(), "materials/")) {
        std::cerr << "Error loading model [" << filename << "] " << err << std::endl;
        exit(1);
    }
    
    texLoader = new TextureLoader(materials);
    
    ShaderProgram *sp = new ShaderProgram((char*)vertexShaderPath.c_str(),
                                          nullptr,
                                          (char*)fragmentShaderPath.c_str());
    
    Doorman *doorman = new Doorman(sp);
    
    for (size_t i = 0; i < shapes.size(); ++i) {
        // TODO: either export model differently or add some case that will fix that
        // For now if shape has no texcoords it won't be rendered
        if (shapes[i].mesh.texcoords.size() > 0) {
            // Each vertex has same material, so for our program this should work :)
            int materialId = shapes[i].mesh.material_ids[0];
            doorman->addMesh(createMesh(shapes[i].mesh, materials[materialId], texLoader->get(materialId)));
        }
    }
    
    return doorman;
}

Mesh* ModelLoader::createMesh(tinyobj::mesh_t mesh, tinyobj::material_t material, Texture* tex) {
    return new Mesh(mesh.positions,
                    mesh.normals,
                    mesh.texcoords,
                    mesh.indices,
                    new Material(material, tex));
}

void ModelLoader::debugPrint(std::vector<tinyobj::shape_t> shapes, std::vector<tinyobj::material_t> materials) {
    std::cout << "# of shapes    : " << shapes.size() << std::endl;
    std::cout << "# of materials : " << materials.size() << std::endl;
    
    for (size_t i = 0; i < shapes.size(); i++) {
        printf("shape[%ld].name = %s\n", i, shapes[i].name.c_str());
        printf("Size of shape[%ld].indices: %ld\n", i, shapes[i].mesh.indices.size());
        printf("Size of shape[%ld].material_ids: %ld\n", i, shapes[i].mesh.material_ids.size());
        assert((shapes[i].mesh.indices.size() % 3) == 0);
        for (size_t f = 0; f < shapes[i].mesh.indices.size() / 3; f++) {
            printf("  idx[%ld] = %d, %d, %d. mat_id = %d\n", f, shapes[i].mesh.indices[3*f+0], shapes[i].mesh.indices[3*f+1], shapes[i].mesh.indices[3*f+2], shapes[i].mesh.material_ids[f]);
        }
        
        printf("shape[%ld].vertices: %ld\n", i, shapes[i].mesh.positions.size());
        assert((shapes[i].mesh.positions.size() % 3) == 0);
        for (size_t v = 0; v < shapes[i].mesh.positions.size() / 3; v++) {
            printf("  v[%ld] = (%f, %f, %f)\n", v,
                   shapes[i].mesh.positions[3*v+0],
                   shapes[i].mesh.positions[3*v+1],
                   shapes[i].mesh.positions[3*v+2]);
        }
    }

    
    for (size_t i = 0; i < materials.size(); i++) {
        printf("material[%ld].name = %s\n", i, materials[i].name.c_str());
        printf("  material.Ka = (%f, %f ,%f)\n", materials[i].ambient[0], materials[i].ambient[1], materials[i].ambient[2]);
        printf("  material.Kd = (%f, %f ,%f)\n", materials[i].diffuse[0], materials[i].diffuse[1], materials[i].diffuse[2]);
        printf("  material.Ks = (%f, %f ,%f)\n", materials[i].specular[0], materials[i].specular[1], materials[i].specular[2]);
        printf("  material.Tr = (%f, %f ,%f)\n", materials[i].transmittance[0], materials[i].transmittance[1], materials[i].transmittance[2]);
        printf("  material.Ke = (%f, %f ,%f)\n", materials[i].emission[0], materials[i].emission[1], materials[i].emission[2]);
        printf("  material.Ns = %f\n", materials[i].shininess);
        printf("  material.Ni = %f\n", materials[i].ior);
        printf("  material.dissolve = %f\n", materials[i].dissolve);
        printf("  material.illum = %d\n", materials[i].illum);
        printf("  material.map_Ka = %s\n", materials[i].ambient_texname.c_str());
        printf("  material.map_Kd = %s\n", materials[i].diffuse_texname.c_str());
        printf("  material.map_Ks = %s\n", materials[i].specular_texname.c_str());
        printf("  material.map_Ns = %s\n", materials[i].specular_highlight_texname.c_str());
        std::map<std::string, std::string>::const_iterator it(materials[i].unknown_parameter.begin());
        std::map<std::string, std::string>::const_iterator itEnd(materials[i].unknown_parameter.end());
        for (; it != itEnd; it++) {
            printf("  material.%s = %s\n", it->first.c_str(), it->second.c_str());
        }
        printf("\n");
    }
}
