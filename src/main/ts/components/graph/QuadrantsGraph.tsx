import {FC} from "react";
import {GraphHolder} from "./GraphHolder";
import {
    BaseGraphDrawer,
    createGraphParams,
    Graph,
    GraphDrawer,
    GraphStyle,
} from "../../logic/GraphDrawer";
import {QuadrantsInfo} from "../../services/quadrants";
import {
    GRAPH_AXIS_COLOR,
    GRAPH_BACK_COLOR,
    GRAPH_COLOR,
    GRAPH_HEIGHT,
    GRAPH_OFFSET,
    GRAPH_TEXT_COLOR,
    GRAPH_WIDTH
} from "../../constants/constants";


export class QuadrantsDrawer extends BaseGraphDrawer<QuadrantsInfo> {
    drawData(graph: Graph, style: GraphStyle, data: QuadrantsInfo): void {
        graph.canvas.fillStyle = style.figure;
        for (const quadrant of data.quadrants) {
            quadrant.draw(graph);
        }
    }
}


export const QuadrantsGraph: FC<any> = () => {
    const graphDrawer: GraphDrawer<QuadrantsInfo> = new QuadrantsDrawer();
    return (<GraphHolder graphDrawer={graphDrawer} graphParams={createGraphParams(GRAPH_WIDTH, GRAPH_HEIGHT, GRAPH_OFFSET)}
                         graphStyle={{background: GRAPH_BACK_COLOR, figure: GRAPH_COLOR, text: GRAPH_TEXT_COLOR, axis: GRAPH_AXIS_COLOR}}/>)
}