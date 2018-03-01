# PersistGenericDAO

Abstração para operações de Create e Add com ServiceBuilder. Além de todas as operações ja implementadas pelo GenericDAO.

## Methods
+ create
+ add

## Usage

#### ServiceBuilder

create
```java
long counter = MyModelServiceUtil.increment(MyModel.class.getName());
MyModel myModel = MyModelServiceUtil.createMyModel(counter);
```

save
```java
MyModel entity = MyModelServiceUtil.addMyModel(myModel);
```

#### PersistGenericDAO
```java
public interface MyModelDAO extends PersistGenericDAO<MyModel>{   
}
```

```java
@Repository
public class MyModelDAOImpl extends PersistGenericDAOImpl<MyModel, MyModelLocalServiceUtil> implements MyModelDAO{
}
```

create 
```java
MyModelDAO dao = new MyModelDAOImpl();
MyModel model = dao.create(new Date(), companyId);
```

save
```java
MyModel entity = dao.save(model);
```