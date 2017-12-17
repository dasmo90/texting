const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require("webpack");

module.exports = {
	resolve: {
		extensions: ['.js', '.ts']
	},
	entry: {
		polyfills: "./src/polyfills.ts",
		main: "./src/app.ts"
	},
	output: {
		path: path.resolve(__dirname, 'target'), // output directory
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
				loader: ["raw-loader", "sass-loader?sourceMap"]
			}
		]
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: "src/index.html",
			inject: "body"
		}),
		new webpack.optimize.CommonsChunkPlugin({
			name: 'polyfills'
		})
	],
	devtool: "source-map",
	devServer: {
		historyApiFallback: true,
		port: 8088
	}
};
