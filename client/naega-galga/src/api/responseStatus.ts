/* eslint-disable no-unused-vars */
enum ResponseStatus {
  Ok = 200,
  Created = 201,
  accepted = 202,
  NoContent = 204,
  BadRequest = 400,
  Unauthorized = 401,
  Forbidden = 403,
  NotFound = 404,
  RequestTimeOut = 408,
  Conflict = 409,
  InternalServerError = 500,
}

export default ResponseStatus;
