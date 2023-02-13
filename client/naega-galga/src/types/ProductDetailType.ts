interface Seller {
  userId: string;
  userIndex: number;
  userName: string;
}
interface Building {
  buildingPark: number;
  buildingRoadAddress: string;
  buildingJibunAddress: string;
  buildingName: string;
  buildingElevator: number;
}
interface Product {
  productType: string;
  productPrice: string;
  productManageCost: number;
  productSize: string;
  productDirection: string;
  productFloor: string;
  productRooms: string;
  productAnimal: string;
  productDetail: string;
}

interface Options {
  optionAirConditioner: boolean;
  optionFridge: boolean;
  optionWashingMachine: boolean;
  optionGasStove: boolean;
  optionInduction: boolean;
  optionMicrowave: boolean;
  optionWifi: boolean;
  optionDesk: boolean;
  optionCloset: boolean;
  optionBed: boolean;
}

interface ProductInfo {
  seller: Seller;
  building: Building;
  product: Product;
  options: Options;
  imagePaths: string[];
}

interface ProductSummry {
  productIndex: number;
  productType: string;
  price: string;
  floor: string;
  managePrice: number;
  sellerId: string;
  sellerIndex: number;
}

export { Seller, Building, Product, Options, ProductInfo, ProductSummry };
