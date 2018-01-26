import {StoryPieceDto} from "./story-piece.dto";
export class GameStatusDto {
    public myGame: boolean;
    public status: number;
    public yourTurn: boolean;
    public whosTurnIndex: number;
    public shownLetters: string;
    public playerNames: string[];
    public currentRound: number;
    public story: StoryPieceDto[];
    public nofShownLetters: number;
    public minLetters: number;
    public maxLetters: number;
    public nofRounds: number;
}
