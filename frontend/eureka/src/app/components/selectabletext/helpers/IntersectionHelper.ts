export class Line{
    public a:number; // a_begin
    public b:number; // a_end
}

export class IntersectionHelper {
    rangesIntersect(a_begin, a_end, b_begin, b_end): boolean {
        return (
            this.isNumberContainedWithin(a_begin, b_begin, b_end) ||
            this.isNumberContainedWithin(a_end, b_begin, b_end) ||
            (this.isNumberSmallerThanBoth(a_begin, b_begin, b_end) && this.isNumberBiggerThanBoth(a_end, b_begin, b_end)) ||
            (this.isNumberSmallerThanBoth(b_begin, a_begin, a_end) && this.isNumberBiggerThanBoth(b_end, a_begin, a_end))
        );
    }

    broadenBorders(a_begin, a_end, b_begin, b_end): Line{
        var line = new Line();
        line.a = a_begin < b_begin ? a_begin : b_begin;
        line.b = a_end > b_end ? a_end : b_end;
        return line;
    }

    isNumberContainedWithin(number, bound_1, bound_2): boolean {
        if (bound_2 < bound_1) {
            var temp = bound_1;
            bound_1 = bound_2;
            bound_2 = temp;
        }

        return (number >= bound_1 && number <= bound_2);
    }

    isNumberBiggerThanBoth(number, bound_1, bound_2): boolean {
        return (number >= bound_1 && number >= bound_2);
    }

    isNumberSmallerThanBoth(number, bound_1, bound_2): boolean {
        return (number <= bound_1 && number <= bound_2);
    }


    getIndicesOf(searchStr, str, startsWith, endsWith): number[] {
        var searchStrLen = searchStr.length;
        if (searchStrLen == 0) {
            return [];
        }

        var addedString = startsWith + str + endsWith;
        var startIndex = 0, index, indices = [];
        index = searchStr.indexOf(addedString);
        indices.push(index + startsWith.length);
        return indices;
    }
    
}