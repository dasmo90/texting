const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const webpack = require("webpack");

module.exports = {
	resolve: {
		extensions: [".js", ".ts", ".scss"]
	},
	entry: {
		polyfills: "./src/polyfills.ts",
		main: "./src/app.ts"
	},
	output: {
		path: path.resolve(__dirname, "target/dist"), // output directory
		filename: "[name].js" // name of the generated bundle
	},
	module: {
		rules: [
			{
				test: /\.html$/,
				loader: "html-loader"
			},
			{
				test: /\.css$/,
				loader: ["style-loader", "css-loader"]
			},
			{
				test: /\.ts$/,
				loader: "awesome-typescript-loader"
			},
			{
				test: /\.ts$/,
				enforce: "pre",
				loader: 'tslint-loader'
			},
			{
				test: /\.scss$/,
				include: path.resolve("src/styles"),
				use: ExtractTextPlugin.extract({
					fallback: 'style-loader',
					use: ['css-loader', 'sass-loader']
				})
			},
			{
				test: /\.scss$/,
				exclude: path.resolve("src/styles"),
				loader: ["raw-loader", "sass-loader?sourceMap"]
			}
		]
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: "src/index.html"
		}),
		new webpack.ContextReplacementPlugin(
			/\@angular(\\|\/)core(\\|\/)esm5/,
			path.join(__dirname, './client')
		),
		new webpack.optimize.CommonsChunkPlugin({
			name: 'polyfills'
		}),
		new ExtractTextPlugin({ filename: 'style.css'})
	],
	devtool: "source-map",
	devServer: {
		historyApiFallback: true,
		port: 8088,
		proxy: [{
			context: ["/game", "/login"],
			target: "http://localhost:8080"
		}]
	}
};
