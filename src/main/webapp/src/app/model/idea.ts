import { Comment } from './comment';

export class Idea {
  id: number;
  text: string;
  dateIdea: Date;
  accepted: boolean;
  voteaverage: number;
  votecounter: number;
  comlist: Array<Comment>;

  constructor() {}
}
