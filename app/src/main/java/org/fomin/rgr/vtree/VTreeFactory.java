package org.fomin.rgr.vtree;

import org.fomin.rgr.types.UserFactory;
import org.fomin.rgr.types.UserType;
import org.json.JSONException;
import org.json.JSONObject;

public class VTreeFactory {
    private UserType typeInstance;
    private UserFactory factory;

    public VTreeFactory(){
        factory = new UserFactory();
        setType(0);
    }
    public UserType getTypeInstance(){
        return typeInstance.create();
    }
    public void setType(int id) {
        typeInstance=factory.getBuilderByName(UserFactory.types.get(id).getClassName());
    }
    public void setType(String name) {
        typeInstance=factory.getBuilderByName(name);
    }
    public VTree parseTree(JSONObject json) throws JSONException {
        Node node = parseNode(json);
        VTree _vtree = new VTree();
        _vtree.SetRoot(node);
        return _vtree;
    }
    public VTree createTree(){
        VTree tree = new VTree();
        return tree;
    }
    private Node parseNode(JSONObject json) throws JSONException {
        String _nodeClassName = json.getString("className");
        assert(_nodeClassName != Node.className);
        JSONObject _valueJSON = null;
        UserType _value = null;
        try{
            _valueJSON = json.getJSONObject("value");
            setType(_valueJSON.getString("className"));
            if (typeInstance == null) {
                throw new Exception("Wrong type");
            }
            _value=typeInstance.parseValue(_valueJSON);
        } catch (Exception e){
            System.out.println(e);
        }

        int _cnt = json.getInt("cnt");
        Node _leftChild = null;
        Node _rightChild = null;
        try{
            _leftChild=parseNode(json.getJSONObject("leftChild"));
        } catch (JSONException e){
        }
        try{
            _rightChild=parseNode(json.getJSONObject("rightChild"));
        } catch (JSONException e){
        }
        Node node = new Node(_value);
        node.setCount(_cnt);
        node.setLeftChild(_leftChild);
        node.setRightChild(_rightChild);
        return node;
    }

}
