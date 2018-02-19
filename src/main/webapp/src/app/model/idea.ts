import { Comment } from './comment';

export class Idea {
  id: number;
  text: string;
  date: any;
  accepted: boolean;
  voteAverage: number;
  voteCounter: number;
  commentsList: Array<Comment>;

  constructor() {}
}
