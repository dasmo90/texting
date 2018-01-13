export class Player {

    constructor(private id: string, private name: string) {
        // no action
    }

    public getName(): string {
        return this.name;
    }

    public getId(): string {
        return this.id;
    }
}
