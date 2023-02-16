interface ProductReservation {
  productIndex: number | undefined;
  meetingIndex: number;
  explanationDate: string;
  sellerIndex: number | null;
  buyerIndex: number | null;
}

export { ProductReservation };
